/**
 * WfHandler 对象, 用于在业务界面打开工作流处理界面
 * 以及在工作流界面处理业务参数和回调等操作。
 *
 * 注意：该对象所有以 _ 开头的属性和函数，外部禁止调用！！！
 *
 * modified by mamin 2013/11/18
 */
var WfHandler = (function () {

    /*对象及常量定义*/
    var handler = {
        DEFAULT_BASE_URL: contextPath + '/wf/handle',
        DEFAULT_BATCH_BASE_URL: contextPath + '/wf/batchHandle',
        WINID_PREFIX: '_ifr_',
        _mode: 'window'
    };

    /************************** 业务界面调用函数开始 **************************/


    /**
     * 初始化 WfHandler
     * @param procInstId    流程实例ID, 从业务对象的 wfId 字段获取
     * @param node          流程节点
     * @param winId         打开的工作流处理窗口的 id， 并用于回调时获取参数。
     * @param title         工作流窗口的标题
     * @param bizTitle      业务窗口的标题，用于在工作流处理完成后，调用回调函数
     * @param callback      回调函数的名称，用于在工作流处理完成后调用
     * @param baseurl       用于处理工作流的 handler。如果自己扩展了 WorkflowController, 需传递该参数, 完整路径
     * @param batch            是否为批量处理，默认false
     * @return Object       返回 WfHandler 对象，可以继续调用其他函数。
     */
    handler.init = function (procInstId/*String*/, node/*String*/, winId/*String*/, title/*String*/, bizTitle/*String*/, callback/*String/Function*/, baseurl/*String*/, batch/*Boolean*/) {
        /*初始化将清空之前添加的属性和业务窗口*/
        delete this._tabs;
        delete this._attrs;

        this._procInstId = procInstId;
        this._node = node;
        this._winId = this.WINID_PREFIX + winId;
        this._title = title;

        if (!bizTitle) {
            var _ts = window.top.$('#tt').find('.tabs-header .tabs-wrap .tabs .tabs-selected .tabs-inner .tabs-title')[0];
            if (_ts) {
                this._bizTitle = $(_ts).text();
            }
        } else {
            this._bizTitle = bizTitle;

        }

        if (!callback) {
            this._callback = 'WfCallback';
        } else {
            this._callback = callback;
        }

        if (!batch) {
            this.batch = false;
        } else {
            this.batch = true;
        }

        if (baseurl) {
            this._baseurl = baseurl;
        }

        return this;
    };

    /**
     * 使用对象来进行初始化，将参数放置到一个对象中
     */
    handler.initOptions = function (options/*Object*/) {
        /*初始化将清空之前添加的属性和业务窗口*/
        delete this._tabs;
        delete this._attrs;

        if (!options || typeof (options) !== 'object') return;

        this._options = $.extend({}, options);

        this._procInstId = this._options.procInstId;
        this._node = this._options.node;
        this._winId = this.WINID_PREFIX + this._options.winId;
        this._title = this._options.title;


        if (!this._options.bizTitle) {
            var _ts = window.top.$('#tt').find('.tabs-header .tabs-wrap .tabs .tabs-selected .tabs-inner .tabs-title')[0];
            if (_ts) {
                this._bizTitle = $(_ts).text();
            }
        } else {
            this._bizTitle = this._options.bizTitle;

        }

        if (!this._options.callback) {
            this._callback = 'WfCallback';
        } else {
            this._callback = this._options.callback;
        }

        if (!this._options.batch) {
            this.batch = false;
        } else {
            this.batch = true;
        }

        if (this._options.baseurl) {
            this._baseurl = this._options.baseurl;
        }


        return this;

    };


    /**
     * 设置流程实例ID
     * @param procInstId    流程实例 ID
     * @return Object       返回 WfHandler 对象，可以继续调用其他函数。
     */
    handler.setProcInstId = function (procInstId/*String*/) {
        this._procInstId = procInstId;
        return this;
    };


    /**
     * 设置流程节点
     * @param node          流程节点
     * @return Object       返回 WfHandler 对象，可以继续调用其他函数。
     */
    handler.setNode = function (node/*String*/) {
        this._node = node;
        return this;
    };


    /**
     * 设置打开的流程处理窗口的ID
     * @param winId         打开窗口ID
     * @return Object       返回 WfHandler 对象，可以继续调用其他函数。
     */
    handler.setWinId = function (winId/*String*/) {
        this._winId = this.WINID_PREFIX + winId;
        return this;
    };

    /**
     * 设置打开的流程处理窗口标题
     * @param title         打开窗口标题
     * @return Object       返回 WfHandler 对象，可以继续调用其他函数。
     */
    handler.setTitle = function (title/*String*/) {
        this._title = title;
        return this;
    };

    /**
     * 设置业务窗口标题
     * @param title         业务窗口标题
     * @return Object       返回 WfHandler 对象，可以继续调用其他函数。
     */
    handler.setBizTitle = function (bizTitle/*String*/) {
        this._bizTitle = bizTitle;
        return this;
    };

    /**
     * 设置回调函数
     * @param callback      回调函数的名称或函数对象
     * @return Object       返回 WfHandler 对象，可以继续调用其他函数。
     */
    handler.setCallback = function (callback/*String/Function*/) {
        this._callback = callback;
        return this;
    };


    /**
     * 设置工作流处理地址
     * @param baseurl       工作流粗粒地址
     * @return Object       返回 WfHandler 对象，可以继续调用其他函数。
     */
    handler.setBaseurl = function (baseurl/*String*/) {
        this._baseurl = baseurl;
        return this;
    };


    /**
     * 增加业务页面
     * @param title         增加的业务页面的标题
     * @param url           增加的业务页面的路径
     * @return Object       返回 WfHandler 对象，可以继续调用其他函数。
     */
    handler.addTab = function (title/*String*/, url/*String*/) {

        if (this._tabs === undefined) {
            this._tabs = [];
        }

        if (title) {
            this._tabs.push({title: title, url: url});
        }

        return this;
    };


    /**
     * 为流程处理窗口增加属性
     * 增加的属性会附加到流程处理窗口的 window 对象上
     * @param name          属性名
     * @param value         属性值
     * @return Object       返回 WfHandler 对象，可以继续调用其他函数。
     */
    handler.addAttr = function (name/*String*/, value/*Object*/) {
        if (this._attrs === undefined) {
            this._attrs = [];
        }

        if (name && value) {
            this._attrs[name] = value;
        }

        return this;
    };


    /**
     * 打开处理工作流的界面
     */
    handler.process = function () {

        /*处理路径*/
        var _url = this._baseurl ? this._baseurl : (this.batch ? this.DEFAULT_BATCH_BASE_URL : this.DEFAULT_BASE_URL);

        /*procInstId, node 和 winId 需要传递给 controller, 其他参数作为 windowArgs 传递*/
        var _params = [];
        _params.push('processId=' + this._procInstId);
        _params.push('node=' + this._node);
        _params.push('winId=' + this._winId);
        if (this._options && this._options.bizToken) {
            _params.push('bizToken=' + this._options.bizToken);
        }

        /*生成 url*/
        var _paramStr = '?' + _params.join('&');
        var _url = _url + _paramStr;

        /*打开流程处理窗口*/
        ;
        if (this._mode == 'window') {
            openWindow(this._winId, this._title, _url,
                {
                    inFrame: true,
                    size: '',
                    width: 900,
                    height: 500,
                    windowArgs: this._prepareWindowArgs()
                });

        } else {
            var _tabset = top.$('#tt');
            if (!_tabset[0]) return;

            /*如果窗口已经打开，显示处理窗口*/
            if (_tabset.tabs('exists', this._title)) {
                _tabset.tabs('select', this._title);
            } else {
                _tabset.tabs('add', {
                    title: this._title,
                    closable: true,
                    cache: false,
                    fit: true,
                    content: '<div style="width:100%;height:100%;overflow:hidden;position:relative"><iframe id="'
                        + this._winId
                        + '" frameborder="0" scrolling="no" '
                        + 'style="width:99.5% !important;height:99.4% !important;height:100%;'
                        + 'padding:2px;overflow: hidden;position:relative" src="'
                        + _url + '" onload="javascript:setIframeHeight(this);"></iframe></div>'
                });

                //将 args 写入打开的窗口对象中
                var _win = top.$('#' + this._winId);
                if (_win[0]) {
                    _win[0]._windowArgs = this._prepareWindowArgs();
                }
            }
        }
    };

    /**
     * 准备给工作流界面的参数
     * 下划线开头的函数, 外部禁止调用
     * @return Object       传递给工作流处理界面的参数
     */
    handler._prepareWindowArgs = function () {
        var args = {
            winId: this._winId,
            title: this._title,
            bizTitle: this._bizTitle,
            callback: this._callback,
            mode: this._mode,
            tabs: this._tabs || [],
            attrs: this._attrs || []
        };

        return args;
    };

    /************************** 业务界面调用函数结束 **************************/


    /************************** 工作流界面调用函数开始 **************************/


    /**
     * 工作流界面获取窗口参数
     * @param winId         工作流窗口ID
     * @return Object       传递给工作流处理界面的参数
     */
    handler.getWindowArgs = function (winId/*String*/) {
        return getWindowArgs(winId);
    };

    /**
     * 关闭工作流处理窗口
     * @param winId         工作流窗口ID
     */
    handler.closeWfWindow = function (winId/*String*/) {
        closeWindow(winId);
    };

    /**
     * 关闭工作流处理窗口
     * @param winId         工作流窗口ID
     */
    handler.closeWfTab = function (winId/*String*/) {
        var _args = this.getWindowArgs(winId);
        if (!_args.title) return;

        var _tabset = top.$('#tt');
        if (!_tabset[0]) return;

        if (_tabset.tabs && _tabset.tabs('exists', _args.title)) {
            _tabset.tabs('close', _args.title);
        }
    };

    /**
     * 回调业务窗口的回调函数
     * @param winId         工作流窗口ID
     * @param oper          操作 approve(通过), reject(退回)
     */
    handler.wfCallback = function (winId/*String*/, oper/*String*/) {
        var _args = this.getWindowArgs(winId);
        if (!_args.bizTitle || !_args.callback) return;

        var _win = this._getBizWindow(_args.bizTitle);
        if (!_win) return;

        var _callback = typeof (_args.callback) === 'string' ? _win[_args.callback] : _args.callback;
        if (typeof (_callback) != 'function') return;

        try {
            _callback.apply(_win, [oper]);
        } catch (e) {
        }

    };

    /**
     * 返回业务处理窗口
     * _ 开头函数，外部禁止调用
     * @param winId         工作流窗口ID
     * @return Object       业务窗口对象
     */
    handler._getBizWindow = function (bizTitle/*string*/) {
        var _tabset = top.$('#tt');
        if (!_tabset[0]) return;

        var _tab = _tabset.tabs('getTab', bizTitle);
        if (!_tab) return;

        var _iframe = _tab.panel('body').find('iframe');
        if (!_iframe[0]) return;

        var _tabFrame = _iframe[0].contentWindow;

        var _todoFrame = _tabFrame.$('iframe[name="todo"]');
        if (!_todoFrame[0] || !_todoFrame[0].contentWindow) return;

        return _todoFrame[0].contentWindow;
    };


    /************************** 工作流界面调用函数结束 **************************/

    return handler;

})();
