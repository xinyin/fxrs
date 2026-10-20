/**
 * 隐藏界面上的某个组件
 * @param id 组件的ID
 */
function hideComponent(id) {
    if (id != null) {
        var comp = document.getElementById(id);
        /*对象存在，直接隐藏*/
        if (comp != null) {
            try {
                comp.style.display = 'none';
            } catch (ex) {
            }
        }
        /*对象不存在，调用回调函数*/
        else {
            try {
                if (fp_callback != undefined) {
                    fp_callback.call(this, id);
                }
            } catch (e) {
            }
        }
    }
}
