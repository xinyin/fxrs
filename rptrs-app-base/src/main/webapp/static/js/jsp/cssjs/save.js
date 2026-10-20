function Save() {

    try {
        var a = WebOffice.Save(strSaveUrl);
        var rs;
        //返回值的内容在upload.jsp里定义，此返回值的开始处字符为"||&"
        //1.查找返回值开始处字符"||&"
        var aStart = a.indexOf('||&');
        //2.查找返回值结束处字符"&||"
        var aStop = a.indexOf('&||');
        //3.提取实际的返回值内容存为rs变量
        if (aStart > -1 && aStop > -1 && aStop > (aStart + 3))//如果为真表示找到返回内容
        {
            rs = a.substring(aStart + 3, aStop);
        }
        if (rs == 'size0') {
            alert('上传的文档大小为0,不成功!');
        } //size0这个字符内容由控件发现保存文档异常时，由控件返回
        else if (rs == 'ok') {
            alert('office文档保存成功!');
            SmartWindowClose();//Save方法保存成功后，建议关闭文档和智能窗有利于控件运行的稳定。
        } else {
            alert('当前返回值为：' + rs + '\n\n具体保存结果，请联系技术人员!');
        }

    } catch (e) {
        alert('发生错误！请使用查阅返回值！');
        alert(a);
    }

}

