window.onload = function () {
    /**
     * js文件路径
     */
    let rootPath = '/common/';
    let implFile = {
        jsPath: [
            rootPath + 'plugin/jquery/jquery-3.4.1.js',
            rootPath + 'plugin/jquery/jquery.min.js',
            rootPath + 'plugin/jquery/jquery.call.interface.js',
            rootPath + 'plugin/layui/layui.js',
            rootPath + 'plugin/layui/layui.all.js',
            rootPath + 'plugin/bootstrap/js/bootstrap.min.js',
            rootPath + 'utils/utils.js',
            rootPath + 'global/global.js',
            'https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/js/bootstrap-select.min.js'
        ],
        cssPath: [
            rootPath + 'plugin/layui/css/layui.css',
            rootPath + 'plugin/bootstrap/css/bootstrap.min.css',
            rootPath + 'css/common.css',
            'https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/css/bootstrap-select.min.css'
        ]
    };

    let headTag = document.getElementsByTagName('head')[0];

    /**
     * 初始化css公共插件
     */
    for (let i = 0; i < implFile.cssPath.length; i++) {
        let link = document.createElement('link');
        link.rel = 'stylesheet';
        link.type = 'text/css';
        link.href = implFile.cssPath[i];
        headTag.appendChild(link);
    }

    let pageJsFile = document.getElementsByTagName('script');
    for (let i = 0; i < pageJsFile.length; i++) {
        let pageSrc = pageJsFile[i].getAttribute('pageSrc');
        if (pageJsFile[i].id == 'groundpush-scrpit' && pageSrc) {
            implFile.jsPath.push(pageSrc);
        }
    }

    /**
     * 初始化js公共的插件
     */
    for (let i = 0; i < implFile.jsPath.length; i++) {
        let script = document.createElement('script');
        script.type = 'text/javascript';
        script.src = implFile.jsPath[i];
        script.async = false;
        if (typeof callback == 'function') {
            script.onload = script.onreadystatechange = function () {
                if (!this.readyState || this.readyState == 'loaded'
                    || this.readyState == 'complete') {
                    script.onload = script.onreadystatechange = null;
                }
            }
        }
        document.body.appendChild(script);
    }
};


