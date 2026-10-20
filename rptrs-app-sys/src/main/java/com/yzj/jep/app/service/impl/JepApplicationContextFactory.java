package com.yzj.jep.app.service.impl;

import com.yzj.jep.app.sys.dto.UserAuthItem;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.web.spring.SpringPermitScanner;
import com.yzj.jep.app.web.spring.SpringPermitScanner.PermitCacheObject;
import com.yzj.jep.core.code.PermitType;
import com.yzj.jep.core.context.IdentityContext;
import com.yzj.jep.core.context.PermitContext;
import com.yzj.jep.core.entity.Identity;
import com.yzj.jep.core.entity.Permission;
import com.yzj.jep.core.factory.ServiceFactory;
import com.yzj.jep.core.service.ContextFactory;
import com.yzj.jep.orm.entity.PO;
import com.yzj.jep.orm.service.EntityService;
import com.yzj.jep.sys.code.ResourceKind;
import com.yzj.jep.sys.model.Menu;
import com.yzj.jep.web.code.WebCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 数据权限上下文工厂
 *
 * @author Michael
 * @time 2017年8月14日 上午10:23:45
 * @company 银之杰技术有限公司
 */
public class JepApplicationContextFactory implements ContextFactory {

    private static final Logger logger = LoggerFactory.getLogger(JepApplicationContextFactory.class);

    /**
     * 查找实体信息的服务
     */
    private static EntityService entityService;

    static {
        entityService = ServiceFactory.createService(EntityService.class);
    }

    public JepApplicationContextFactory() {
    }

    private String getFormedRequestURI(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String ctx = request.getContextPath();
        if (requestURI.startsWith(ctx)) {
            requestURI = StringUtils.substringAfter(requestURI, ctx);
        }

        return requestURI;
    }

    /**
     * 从 UserProfile 中获取用户身份信息
     *
     * @param profile
     * @return
     */
    private Identity createIdentity(UserProfile profile) {
        IdentityImpl identity = new IdentityImpl();
        identity.setId(profile.getUserid());
        identity.setName(profile.getStaffName());
        identity.setUnitCode(profile.getUnitCode());
        identity.setUnitName(profile.getUnitName());
        identity.setDeptCode(profile.getDeptCode());
        identity.setDeptName(profile.getDeptName());
        identity.setTenantId(profile.getTenantId());
        return identity;
    }

    @Override
    public PermitContext createPermitContext(HttpServletRequest request) {

        PermitContext context = new PermitContext();
        UserProfile profile = UserProfileUtils.getUserProfile();

        if (profile != null) {
            context.setIdentity(createIdentity(profile));

            String uri = getFormedRequestURI(request);
            logger.info("request uri : " + uri);
            if (SpringPermitScanner.isPermit(uri)) {
                PermitCacheObject pco = SpringPermitScanner.getCachedObject(uri);
                Menu menu = pco.getMenu();
                logger.info("menu: " + menu);

                String[] permitFields = pco.getPermitFields();

                /*检查定义的 permitFields 是否在entity定义的数据权限的范围之内*/
                boolean fieldOk = true;
                if (permitFields != null) {
                    for (String field : permitFields) {
                        if (!entityService.isPermitedField(pco.getEntityClass(), field)) {
                            fieldOk = false;
                        }
                    }
                } else {
                    fieldOk = false;
                }

                if (!fieldOk) {
                    logger.info("[" + uri + "] field check failed, not cache.");
                } else {
                    PermissionImpl permission = new PermissionImpl(pco.getEntityClass(), pco.getPermitFields());
                    /*获取当前用户的数据权限*/
                    Map<String, List<UserAuthItem>> map = profile.getAuthItems();

                    //TODO 测试代码
//					UserAuthItem uai = new UserAuthItem();
//					uai.setKind(ResourceKind.PERMIT);
//					uai.setResourceCode(PermitType.UNIT.name());
//					uai.setResourceData(menu.getMenuCode());
//					map.put(menu.getMenuCode(), Arrays.asList(new UserAuthItem[]{uai}));
                    //TODO 测试代码

                    if (map != null && map.size() > 0 && map.containsKey(menu.getMenuCode())) {
                        List<UserAuthItem> list = map.get(menu.getMenuCode());
                        for (UserAuthItem item : list) {
                            if (item.getResourceCode() == null) {
                                continue;
                            }
                            if (item.getKind() == ResourceKind.PERMIT) {
                                try {
                                    PermitType type = Enum.valueOf(PermitType.class, item.getResourceCode());
                                    if (type != null) {
                                        if (permission.getPermitType() == null || permission.getPermitType().getWeight() < type.getWeight()) {
                                            permission.setPermitType(type);
                                        }
                                    }
                                } catch (Exception ex) {
                                }
                            }
                        }
                    }
                    //如果没有授权信息，禁止访问
                    if (permission.getPermitType() == null) {
                        permission.setPermitType(PermitType.FORBIDDEN);
                    }
                    context.setPermission(permission);
                }
            }
        }

        return context;
    }


    class IdentityImpl implements Identity {

        private String id;

        private String name;

        private String unitCode;

        private String unitName;

        private String deptCode;

        private String deptName;

        private String tenantId;

        @Override
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String getUnitCode() {
            return unitCode;
        }

        public void setUnitCode(String unitCode) {
            this.unitCode = unitCode;
        }

        @Override
        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        @Override
        public String getDeptCode() {
            return deptCode;
        }

        public void setDeptCode(String deptCode) {
            this.deptCode = deptCode;
        }

        @Override
        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        @Override
        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }
    }

    /**
     * 授权信息
     *
     * @author michael
     * @company 银之杰技术有限公司
     * @time 2018年3月1日 上午9:27:43
     * @since
     */
    class PermissionImpl implements Permission {

        public PermissionImpl(Class<? extends PO> entityClass, String[] permitFields) {
            this.entityClass = entityClass;
            this.permitFields = permitFields;
        }

        private Class<? extends PO> entityClass;

        private PermitType permitType;

        private String[] permitFields;

        @Override
        public Class<?> getEntityClass() {
            return entityClass;
        }

        @Override
        public String[] getPermitFields() {
            return permitFields;
        }

        @Override
        public PermitType getPermitType() {
            return permitType;
        }

        public void setEntityClass(Class<? extends PO> entityClass) {
            this.entityClass = entityClass;
        }

        public void setPermitType(PermitType permitType) {
            this.permitType = permitType;
        }

        public void setPermitFields(String[] permitFields) {
            this.permitFields = permitFields;
        }

    }

    @Override
    public IdentityContext createIdentityContext(HttpServletRequest request) {
        IdentityContext context = null;
        UserProfile profile = UserProfileUtils.getUserProfile();

        if (profile == null) {
            context = new IdentityContext();
        } else {
            context = new IdentityContext(createIdentity(profile));
            /*请求中包含 tenantRequired，并且为 false 时，关闭过滤*/
            String tenantRequired = request.getParameter(WebCode.PARAM_TENANT_REQUIRED);
            if ("false".equalsIgnoreCase(tenantRequired)) {
                context.setTenantRequired(false);
            }
        }

        return context;
    }

}
