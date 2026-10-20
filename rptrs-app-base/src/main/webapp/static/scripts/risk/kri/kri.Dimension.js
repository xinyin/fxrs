var strPath = window.top.location.pathname;
var postPath = strPath.substring(0, strPath.substring(1).indexOf('/') + 1);
var unitTypeData;
var queryHierarchy;
var unitDimension = "DIMENSION_01";
$.post(postPath + "/fixedDict/UnitHierarchy", function (data) {
    if (data && data.length > 0) {
        unitTypeData = data;
    }
});
$.post(postPath + "/unit/queryHierarchy?beginStr=null", function (data) {
    if (data && data.length > 0) {
        queryHierarchy = data;
    }
});
var editNodeArray;

function initDimensionTree(dimensionCode, divID, height, dimensionData) {
    var parentDiv = $("#" + divID);
    parentDiv.html("");
    var len = dimensionCode.length;
    if (len != 0) {
        parentDiv.css("display", "");
    } else {
        parentDiv.css("display", "none");
    }
    var total = 99.6;
    if (len > 2) total = 99.2;
    if (len > 4) total = 98.8;
    var present = total / len;
    $.each(dimensionCode, function (i, n) {
        var title = getDimensionName(n, dimensionData);
        var treeId = n + "_tree";
        var div = '<span style="width:' + present +
            '%;height:' + height + 'px;text-align:left;border:1px #b9d5f6 solid;margin:1px;"><div class="panel-header panel-header-noborder" style="height:22px;">' + title +
            '维度</div><div style="width:100%;height:' + (height - 32) + 'px;overFlow-y: auto;">';
        if (n == unitDimension) {
            /*div += '<input id= "unit_'+n+'"  style="width:140px;"/>';*/
            if (queryHierarchy) {
                for (i = 0; i < queryHierarchy.length; i++) {
                    div += "<input type='checkbox' name='checkHierarchy' id='unit_" + n + "' value='" + queryHierarchy[i].value + "' />" + queryHierarchy[i].name + "&nbsp";
                }
            }
            /* $("#" + divID).html(div); */

        } else {
            div += '<input type="checkbox" name="' + treeId + '" onClick="selectAllItem(this)"/>全选';
        }
        $("#dimensionDiv :checkbox").click(function () {
            var value = $(this).val();
            if ($(this).attr("checked")) {
                selectedUnitDimension("1", value);
            } else {
                selectedUnitDimension("0", value);
            }
        });
        div += '<ul id="' + treeId + '" ></ul></div></span>';
        $(div).appendTo(parentDiv);
        $('#' + treeId).tree({
            checkbox: true,
            //cascadeCheck:false,
            method: 'get',
            url: postPath + '/kri/dimensionData/findByDimensionCode?dimensionCode=' + n,
            //单击
            onClick: function (node) {
                $(this).tree("toggle", node.target);
            },
            //右键弹出菜单
            onContextMenu: contextMenu,
            //加载数据成功
            onLoadSuccess: function (node, data) {
                var root = $(this).tree('getRoot');
                if (root == null) {
                    $(this).tree('append', {
                        parent: root,
                        data: [{
                            id: '-1',
                            text: '无'
                        }]
                    });
                } else
                    $(this).tree('expand', root.target);
                if (editNodeArray) {
                    checkedDimensionData(editNodeArray, treeId);
                }
            }
        });
        if (n == unitDimension) {
            $('#' + treeId).tree({
                cascadeCheck: false
            });
        }
    });
}

function getDimensionName(code, dimensionData) {
    for (var j = 0; j < dimensionData.length; j++) {
        if (code == dimensionData[j].id) {
            return dimensionData[j].text;
        }
    }
    return "";
}

//初始化编辑维度及维度数据
function initEditDimension(dimensionsCodes) {
    var array = new Array();
    var ddArray = new Array();
    if (!dimensionsCodes) return;
    var dimensionDatas = dimensionsCodes.split(";");
    for (var i = 0; i < dimensionDatas.length; i++) {
        var dd = dimensionDatas[i];
        if (dd) {
            var dimensions = dd.split(":");
            if (dimensions && dimensions.length > 0) {
                var dimensionCode = dimensions[0];
                if (dimensionCode) {
                    array.push(dimensionCode);
                    if (dimensions.length > 1) {
                        ddArray.push({"id": dimensionCode, "value": initEditDimensionData(dimensions[1])});
                    }
                }
            }
        }
    }
    editNodeArray = ddArray;
    return array;
}

function initEditDimensionData(codes) {
    var array = new Array();
    if (!codes) return;
    var codeArr = codes.split(",");
    if (codeArr) {
        for (var i = 0; i < codeArr.length; i++) {
            var code = codeArr[i];
            if (code) {
                array.push(code);
            }
        }
    }
    return array;
}

function saveDimensions(selectedDimension) {
    var dimensionsCodes = "";
    if (selectedDimension) {
        $.each(selectedDimension, function (i, n) {
            dimensionsCodes += dimensionsCodes == "" ? n + ":" : ";" + n + ":";
            var checkNodes = getCheckedAll(n);
            $.each(checkNodes, function (i, node) {
                if (node && node.id != "-1") {
                    if (dimensionsCodes.substring(dimensionsCodes.length - 1) == ":") {
                        dimensionsCodes += node.id;
                    } else {
                        dimensionsCodes += "," + node.id;
                    }
                } else {
                    return false;
                }
            });
        });
    }
    return dimensionsCodes;
}

//用于阈值设置
function getDimensions() {
    if (selectedDimension) {
        //$.each(selectedDimension,function(i,n){
        for (var i = 0; i < selectedDimension.length; i++) {
            var n = selectedDimension[i];
            if (n == unitDimension) {
                var dimensions = "";
                var checkNodes = getCheckedAll(n);
                //$.each(checkNodes, function(k, node){
                for (var k = 0; k < checkNodes.length; k++) {
                    var node = checkNodes[k];
                    if (node && node.id != "-1") {
                        var d = node.id + ":" + node.text;
                        dimensions += dimensions == "" ? d : "," + d;
                    } else {
                        return "";
                    }
                }//);
                return dimensions;
            }
        }//);
    }
}

function selectedUnitDimension(type, value) {
    var treeObj = $('#' + unitDimension + '_tree');
    var roots = treeObj.tree("getRoots");
    if (type == "0") {
        uncheckNode(treeObj, value, roots);
    } else {
        checkedNode(treeObj, value, roots);
    }
}

function selectAllItem(obj) {
    var treeObj = $("#" + obj.name);
    var roots = treeObj.tree("getRoots");
    $.each(roots, function (i, rootNode) {
        if ($(obj).attr("checked")) {
            treeObj.tree("check", rootNode.target);
        } else {
            treeObj.tree("uncheck", rootNode.target);
        }
    });
}

function contextMenu(e) {
    e.preventDefault();
}

function getCheckedAll(n) {
    var treeObj = $("#" + n + "_tree");
    return treeObj.tree("getChecked");
}

function checkedDimensionData(dimensions, treeId) {
    $.each(dimensions, function (j, dimension) {
        if (treeId == dimension.id + "_tree") {
            var treeObj = $("#" + treeId);
            $.each(dimension.value, function (i, code) {
                var node = treeObj.tree("find", code);
                treeObj.tree("check", node.target);
            });
        }
    });
}

function uncheckNode(treeObj, code, nodes) {
    $.each(nodes, function (i, node) {
        if (node.attributes.type == code) {
            treeObj.tree("uncheck", node.target);
        }
        if (!treeObj.tree("isLeaf", node.target)) {
            var children = treeObj.tree("getChildren", node.target);
            uncheckNode(treeObj, code, children);
        }
    });
}

function checkedNode(treeObj, code, nodes) {
    $.each(nodes, function (i, node) {
        if (node.attributes.type == code) {
            treeObj.tree("check", node.target);
        }
        if (!treeObj.tree("isLeaf", node.target)) {
            var children = treeObj.tree("getChildren", node.target);
            checkedNode(treeObj, code, children);
        }
    });
}

function readOnlyTree(dimensionCode) {
    $.each(dimensionCode, function (i, n) {
        var treeId = n + "_tree";
        $("#" + treeId).attr('disabled', "disabled");
    });
}