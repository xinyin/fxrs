package com.yzj.jep.app.web.spring;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ClassUtils;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yzj.jep.commons.AnnotationUtils;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.GenericsUtils;
import com.yzj.jep.core.code.PermitType;
import com.yzj.jep.orm.entity.PO;
import com.yzj.jep.sys.model.Menu;
import com.yzj.jep.sys.service.MenuService;
import com.yzj.jep.web.annotation.PermitController;
import com.yzj.jep.web.annotation.PermitRequired;

/**
 * 初始化数据权限
 *
 * @author Michael
 * @time 2017年8月10日 下午9:42:42
 * @company 银之杰技术有限公司
 */
public class SpringPermitScanner implements ResourceLoaderAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(SpringPermitScanner.class);
    private ResourcePatternResolver resourcePatternResolver;
    private MetadataReaderFactory metadataReaderFactory = new SimpleMetadataReaderFactory();
    private static PathMatcher pathMatcher = new AntPathMatcher();


    private static final String CLASS_RESOURCE_PATTERN = "**/*.class";
    private final Class<PermitController> PERMIT_CONTROLLER_ANNOTATION = PermitController.class;
    private final Class<PermitRequired> PERMIT_REQUIRED_ANNOTATION = PermitRequired.class;
    private final Class<Controller> CONTROLLER_ANNOTATION = Controller.class;
    private final Class<RequestMapping> REQUEST_MAPPING_ANNOTATION = RequestMapping.class;

    private TypeFilter permitControllerFilter = new AnnotationTypeFilter(PERMIT_CONTROLLER_ANNOTATION);
    private TypeFilter controllerFilter = new AnnotationTypeFilter(CONTROLLER_ANNOTATION);

    @Autowired
    private MenuService menuService;

    private List<String> packages;
    private List<Menu> menus;
    private Map<String, Menu> menuMap = new HashMap<>();


    public List<String> getPackages() {
        return packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        menus = menuService.selectLeafMenus();
        if (CollectionUtils.isEmpty(menus)) {
            logger.info("No menus defined, ignore privilege processing");
            return;
        } else {
            /*缓存所有 menu 定义*/
            for (Menu menu : menus) {
                menuMap.put(correctUrl(menu.getUrl()), menu);
                PermitCache.cacheMenu(menu);
            }
        }

        if (CollectionUtils.isEmpty(packages)) {
            logger.info("No packages defined, ignore privilege processing");
            return;
        }

        processPermit();
    }


    /**
     * 处理权限
     *
     * @throws Exception
     */
    @SuppressWarnings({"unused", "rawtypes", "unchecked"})
    private void processPermit() throws Exception {
        logger.info("start permit processing");

        try {

            for (String p : packages) {
                String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                        + ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(p)) + "/" + CLASS_RESOURCE_PATTERN;

                Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
                /*最终需要保存的路径和权限的匹配*/
                Map<String, List<PermitType>> permitMap = new HashMap<String, List<PermitType>>();

                /*每个 resource 为一个 class*/
                for (Resource resource : resources) {
                    if (!resource.isReadable()) {
                        continue;
                    }
                    MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
                    /*controller*/
                    if (!controllerFilter.match(metadataReader, metadataReaderFactory)) {
                        continue;
                    }

                    /*是否为权限控制的 controller */
                    if (!permitControllerFilter.match(metadataReader, metadataReaderFactory)) {
                        continue;
                    }
                    Class clazz = Class.forName(metadataReader.getAnnotationMetadata().getClassName());
                    Class entityClass = GenericsUtils.getSuperClassGenricType(clazz);
                    logger.info("process controller " + clazz.getCanonicalName());
                    logger.info("entityClass: " + entityClass.getCanonicalName());


                    List<String> list = new ArrayList<>();
                    Menu menu = null;

                    /*取出所有定义了 RequestMapping 的方法，取出其 value 的定义，查找并匹配路径*/
                    Map<Method, RequestMapping> mappingMethodMap = AnnotationUtils.findAnnotatedMethods(clazz, REQUEST_MAPPING_ANNOTATION);
                    for (Entry<Method, RequestMapping> entry : mappingMethodMap.entrySet()) {
                        Method method = entry.getKey();
                        String[] urls = determineUrlsForControllerMapping(clazz, method);
                        if (CollectionUtils.isEmpty(urls)) {
                            continue;
                        }

                        /*确定当前 controller 对应的 menu */
                        for (String url : urls) {
                            if (menuMap.containsKey(url)) {
                                menu = menuMap.get(url);
                                break;
                            }
                        }
                        list.addAll(Arrays.asList(urls));
                    }


                    /*当前 controller 没有对应menu， 略过*/
                    if (menu == null) {
                        continue;
                    }

                    /*将全部  url 缓存*/
                    PermitCache.cacheMapping(list, menu, entityClass, true, null);
                    list.clear();

                    /*取出所有定义了 PrivilegeRequired 的方法，查找并匹配路径*/
                    Map<Method, PermitRequired> permitMethodMap = AnnotationUtils.findAnnotatedMethods(clazz, PERMIT_REQUIRED_ANNOTATION);
                    for (Entry<Method, PermitRequired> entry : permitMethodMap.entrySet()) {
                        Method method = entry.getKey();
                        PermitRequired permitRequired = entry.getValue();
                        String[] fields = permitRequired.value();

                        String[] urls = determineUrlsForControllerMapping(clazz, method);
                        if (CollectionUtils.isEmpty(urls)) {
                            continue;
                        }

                        /*缓存*/
                        PermitCache.cacheMapping(Arrays.asList(urls), menu, entityClass, true, fields);
                    }
                }

                List<PermitCacheObject> list = PermitCache.getAllCachedObject();
                for (PermitCacheObject pco : list) {
                    logger.info(pco.toString());
                }

            }

        } catch (Exception ex) {
            throw new RuntimeException("I/O failure during classpath scanning", ex);
        }

        logger.info("Finish permit initilizing");
    }


    /**
     * 将 clazz 指定的 controller 的 method 方法上所定义的 value 取出来返回，可以是多个.
     * 取出该 method 的 RequestMapping 指定的 value, 如果 controller 定义了 RequestMapping,
     * 则返回包括 controller 路径的完整路径
     *
     * @param clazz
     * @param method
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private String[] determineUrlsForControllerMapping(Class clazz, Method method) {
        RequestMapping mapping = (RequestMapping) clazz.getAnnotation(REQUEST_MAPPING_ANNOTATION);
        if (mapping != null) {
            final Set<String> urls = new LinkedHashSet<String>();
            String[] typeLevelPatterns = mapping.value();
            if (typeLevelPatterns.length > 0) {
                String[] methodLevelPatterns = determineUrlsForControllerMethodMapping(clazz, method, true);
                for (String typeLevelPattern : typeLevelPatterns) {
                    typeLevelPattern = correctUrl(typeLevelPattern);
                    boolean hasEmptyMethodLevelMappings = false;
                    for (String methodLevelPattern : methodLevelPatterns) {
                        if (methodLevelPattern == null) {
                            hasEmptyMethodLevelMappings = true;
                        } else {
                            String combinedPattern = pathMatcher.combine(typeLevelPattern, methodLevelPattern);
                            addUrlsForPath(urls, combinedPattern);
                        }
                    }
                    if (hasEmptyMethodLevelMappings) {
                        addUrlsForPath(urls, typeLevelPattern);
                    }
                }
                return StringUtils.toStringArray(urls);
            } else {
                return determineUrlsForControllerMethodMapping(clazz, method, false);
            }
        } else {
            return determineUrlsForControllerMethodMapping(clazz, method, false);
        }
    }

    /**
     * 将 clazz 指定的 controller 的 method 方法上所定义的 value 取出来返回，可以是多个.
     * 如果 PrivilegeMapping 指定了 value, 则返回该 value，否则，取出该 method 的
     * RequestMapping 指定的 value
     *
     * @param clazz
     * @param method
     * @return
     */
    @SuppressWarnings("rawtypes")
    private String[] determineUrlsForControllerMethodMapping(Class clazz, Method method, boolean hasTypeLevelPattern) {
        final Set<String> urls = new LinkedHashSet<String>();
        RequestMapping mapping = org.springframework.core.annotation.AnnotationUtils.findAnnotation(method, RequestMapping.class);
        if (mapping != null) {
            if (mapping != null) {
                String[] mappedPatterns = mapping.value();
                if (mappedPatterns.length > 0) {
                    for (String mappedPattern : mappedPatterns) {
                        if (!hasTypeLevelPattern && !mappedPattern.startsWith("/")) {
                            mappedPattern = correctUrl(mappedPattern);
                        }
                        addUrlsForPath(urls, mappedPattern);
                    }
                }
            }
        }
        return StringUtils.toStringArray(urls);
    }

    private void addUrlsForPath(Set<String> urls, String path) {
        urls.add(path);
        if (path.indexOf('.') == -1 && !path.endsWith("/")) {
            urls.add(path + ".*");
            urls.add(path + "/");
        }
    }


    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
    }

    /**
     * 修正不规范的 url
     *
     * @param url
     * @return
     */
    private static String correctUrl(String url) {
        if (!url.startsWith("/")) {
            return "/" + url;
        }
        return url;
    }

    /**
     * 判断一个菜单是否是受数据权限控制的
     *
     * @param menuCode
     * @return
     */
    public static boolean isPermitMenu(String menuCode) {
        Menu menu = PermitCache.getCachedMenu(menuCode);
        if (menu != null) {
            return isPermit(correctUrl(menu.getUrl()));
        }
        return false;
    }

    /**
     * 获取缓存对象
     *
     * @param menuCode
     * @return
     */
    public static PermitCacheObject getMenuCachedObject(String menuCode) {
        Menu menu = PermitCache.getCachedMenu(menuCode);
        if (menu != null) {
            return getCachedObject(correctUrl(menu.getUrl()));
        }
        return null;
    }

    /**
     * 判断一个 uri 是否进行权限控制
     *
     * @param uri
     * @return
     */
    public static boolean isPermit(String uri) {
        return PermitCache.isPermit(uri);
    }

    /**
     * 返回一个  CacheObject 对象
     *
     * @param uri
     * @return
     */
    public static PermitCacheObject getCachedObject(String uri) {
        return PermitCache.getCachedObject(uri);
    }

    /**
     * 返回所有缓存对象
     *
     * @return
     */
    public static List<PermitCacheObject> getAllCachedObject() {
        return PermitCache.getAllCachedObject();
    }


    /**
     * 权限控制缓存对象
     *
     * @author Michael
     * @time 2017年8月14日 上午10:59:24
     * @company 银之杰技术有限公司
     */
    public static class PermitCacheObject {

        public PermitCacheObject(String uri, Menu menu, Class<? extends PO> entityClass) {
            this.uri = uri;
            this.menu = menu;
            this.entityClass = entityClass;
        }

        /**
         * 匹配的 url
         */
        private String uri;

        /**
         * 对应的 menu
         */
        private Menu menu;

        /**
         * 是否权限控制
         */
        private boolean permit;

        /**
         * 数据权限控制的字段
         */
        private String[] permitFields;

        /**
         * 对应的实体
         */
        private Class<? extends PO> entityClass;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public Menu getMenu() {
            return menu;
        }

        public void setMenu(Menu menu) {
            this.menu = menu;
        }

        public boolean isPermit() {
            return permit;
        }

        public void setPermit(boolean permit) {
            this.permit = permit;
        }

        public String[] getPermitFields() {
            return permitFields;
        }

        public void setPermitFields(String[] permitFields) {
            this.permitFields = permitFields;
        }

        public Class<? extends PO> getEntityClass() {
            return entityClass;
        }

        public void setEntityClass(Class<? extends PO> entityClass) {
            this.entityClass = entityClass;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder("Cached Object [");
            builder.append(", uri = ").append(uri);
            builder.append(", menu = ").append(menu.getMenuCode());
            builder.append(", permit = ").append(permit);
            builder.append(", entityClass = ").append(entityClass);
            if (CollectionUtils.isNotEmpty(permitFields)) {
                builder.append(", fields = [");
                for (String field : permitFields) {
                    builder.append(field).append(", ");
                }
                builder.delete(builder.length() - 2, builder.length());
                builder.append("]");
            }
            builder.append("]");
            return builder.toString();
        }

    }


    /**
     * 缓存
     *
     * @author Michael
     * @time 2017年9月5日 下午2:34:11
     * @company 银之杰技术有限公司
     */
    private static class PermitCache {

        private static final Logger logger = LoggerFactory.getLogger(PermitCache.class);

        /**
         * code 与  Menu 映射
         */
        private static ConcurrentHashMap<String, Menu> menus = new ConcurrentHashMap<>();

        /**
         * uri 与   CacheObject 的映射
         */
        private static ConcurrentHashMap<String, PermitCacheObject> cache = new ConcurrentHashMap<String, PermitCacheObject>();

        private PermitCache() {
        }

        /**
         * 判断一个 uri 是否进行权限控制
         *
         * @param uri
         * @return
         */
        public static boolean isPermit(String uri) {
            if (cache.containsKey(uri)) {
                return cache.get(uri).isPermit();
            }
            return false;
        }

        /**
         * 返回一个  CacheObject 对象
         *
         * @param uri
         * @return
         */
        public static PermitCacheObject getCachedObject(String uri) {
            if (cache.containsKey(uri)) {
                return cache.get(uri);
            }
            return null;
        }

        /**
         * 返回所有缓存对象
         *
         * @return
         */
        public static List<PermitCacheObject> getAllCachedObject() {
            return new ArrayList<>(cache.values());
        }


        /**
         * 新增 Menu 映射
         *
         * @param menu
         */
        public static void cacheMenu(Menu menu) {
            if (menu != null) {
                menus.put(menu.getMenuCode(), menu);
            }
        }

        /**
         * 获得菜单定义
         *
         * @param menuCode
         * @return
         */
        public static Menu getCachedMenu(String menuCode) {
            if (menus.containsKey(menuCode)) {
                return menus.get(menuCode);
            }
            return null;
        }


        /**
         * 缓存所有 uri
         *
         * @param uri
         * @param menu
         * @param entityClass
         * @param permit
         * @param permitFields
         */
        public static void cacheMapping(List<String> list, Menu menu, Class<? extends PO> entityClass, boolean permit, String[] permitFields) {
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            for (String uri : list) {
                cacheMapping(uri, menu, entityClass, permit, permitFields);
            }
        }

        /**
         * 缓存 uri
         *
         * @param uri
         * @param menu
         * @param entityClass
         * @param permit
         * @param permitFields
         */
        public static void cacheMapping(String uri, Menu menu, Class<? extends PO> entityClass, boolean permit, String[] permitFields) {
            logger.debug("cache uri: " + uri);
            PermitCacheObject pco = cache.get(uri);
            if (pco == null) {
                pco = new PermitCacheObject(uri, menu, entityClass);
                cache.put(uri, pco);
            }

            pco.setPermit(permit);
            if (CollectionUtils.isNotEmpty(permitFields)) {
                pco.setPermitFields(permitFields);
            }
            cacheMenu(menu);
        }

    }

}

