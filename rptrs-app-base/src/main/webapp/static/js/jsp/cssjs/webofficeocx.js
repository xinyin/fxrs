if (!!window.ActiveXObject || "ActiveXObject" in window) {
    document.write('<object classid="clsid:FF09E4FA-BFAA-486E-ACB4-86EB0AE875D5" codebase="WebOffice.ocx#Version=2018,1,6,2" id="WebOffice" width="100%" height="900" >');
    document.write('<param name="Menubar" value="1">  ');
    document.write('<param name="Titlebar" value="1">');
    document.write('<param name="Toolbars" value="1">');
    document.write('</object>');
} else {
    document.write('<object id="WebOffice" CLSID="{FF09E4FA-BFAA-486E-ACB4-86EB0AE875D5}" TYPE="application/x-itst-activex"  width=100% height=900></object>');
}

function setMenu() {
    WebOffice = document.getElementById('WebOffice');//获得控件对象
    if (WebOffice) setTimeout('editMenu()', 100);
}

function editMenu() {
    WebOffice.WebDeleteMenuItem(0, 0);//清除本地文件菜单第1项
    WebOffice.WebDeleteMenuItem(0, 0);//清除本地文件菜单第2项
    WebOffice.WebDeleteMenuItem(0, 0);//清除本地文件菜单第3项
    WebOffice.WebDeleteMenuItem(0, 0);//清除本地文件菜单第4项
    WebOffice.WebDeleteMenuItem(0, 0);//清除本地文件菜单第5项
}

function SmartWindowClose() {
    try {
        window.opener = null;
        window.open('', '_self', '');
        window.close();
    } catch (e) {
        alert("信息提示：\r\n出错内容:" + e + "\r\n错误代码:" + e.number + "\r\n错误描述:" + e.description);
    }
}
