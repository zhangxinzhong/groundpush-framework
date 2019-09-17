window.onload = function () {
    /**
     * js文件路径
     */
    let rootPath = '/common/plugin';
    let implFile = {
        jsPath: [
            rootPath + '/jquery/jquery-3.4.1.js',
            rootPath + '/jquery/jquery.min.js',
            rootPath + '/jquery/jquery.call.interface.js'
        ]
    };

    let headTag = document.getElementsByTagName('head')[0];


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


