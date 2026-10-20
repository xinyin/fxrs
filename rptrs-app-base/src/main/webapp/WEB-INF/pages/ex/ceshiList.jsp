<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
	<!-- 引入 ECharts 文件 -->
	<script src="https://lib.baomitu.com/echarts/4.6.0/echarts.min.js"></script>
	<script type="text/javascript" src="https://lib.baomitu.com/jquery/3.4.1/jquery.js"></script>
	<script src="https://mbostock.github.com/d3/d3.v2.js"></script>
	<br:css pack="list"/>
<title>Staff</title>
<x:jsLib/>
	<style>
		/*.node circle {*/
			/*cursor: pointer;*/
			/*fill: #fff;*/
			/*stroke: steelblue;*/
			/*stroke-width: 1.5px;*/
		/*}*/

		/*.node text {*/
			/*font: 10px sans-serif;*/
		/*}*/

		/*path.link {*/
			/*fill: none;*/
			/*stroke: #ccc;*/
			/*stroke-width: 1.5px;*/
		/*}*/
        /*body {*/
            /*text-align: center;*/
        /*}*/

        /*svg {*/
            /*margin-top: 32px;*/
            /*border: 1px solid #aaa;*/
        /*}*/

        /*.person rect {*/
            /*fill: #fff;*/
            /*stroke: rgba(0, 0, 0, 0.15);*/
            /*stroke-width: 1px;*/

        /*}*/

        /*.person {*/
            /*font: 14px sans-serif;*/
        /*}*/

        /*.btn {*/
            /*cursor: pointer;*/
        /*}*/

        /*.link {*/
            /*!* fill: none;*/
            /*stroke: rgba(0, 0, 0, 0.15);*/
            /*stroke-width: 1.5px; *!*/
            /*fill: none;*/
            /*stroke: #ccc;*/
            /*stroke-width: 1.5px;*/

        /*}*/

        /*.btn circle {*/
            /*stroke: rgba(0, 0, 0, 0.15);*/
            /*fill: #fff;*/
        /*}*/
	</style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<%--<div class="row">--%>
	<%--企业图谱--%>
	<%--</div>--%>
	<%--echarts test--%>
	<%--<div id="main" style="width: 600px; height: 400px;background:gray"></div>--%>
	<%--D3.js test--%>
	<%--<div id="chart"></div>--%>
        <div class="container" id="treecontainer">
            <div class="menu">
                <div id="product_tree"></div>
            </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<%--<script src="../../static/js/dstes.json"></script>--%>
<%--<script src="https://cdn.bootcss.com/d3/3.5.5/d3.js"></script>--%>
<script src="https://cdn.bootcss.com/d3/3.2.7/d3.min.js"></script>
<script>

    <%--$(function(){--%>
        <%--$.ajax({--%>
            <%--type: 'GET',--%>
            <%--url: '${base}/bus/sequence/list',--%>
            <%--dataType: 'json',--%>
            <%--success:function(data){--%>
                <%--console.log(data);--%>
                <%----%>
            <%--}--%>
        <%--});--%>
    <%--})--%>
    var container;
    var zoom;
    var rootData;
    var depthInfo;
    var rootName; //根节点名称

    jQuery(document).ready(function () {
        /* resizeScreen();*/

        //selectChange();
        var child = document.getElementById("product_tree");
        child.innerHTML = '';
        getData();
    });

    var getData = function () {
        // 测试内容
		var params;
		$.get({
			url: "http://192.168.1.245:8090/relOrg/select?name=成都农商银行&limit= 10",
			"xhrFields": {
				"withCredentials": true
			},
			"crossDomain": true,
			success: function(data){
				console.log(data);
				rootData = data

				rootName = '成都农商银行';
				drawing();
			}
		});

    };
	var drawing = function () {
		var _this = this;
		// var rootName = ''; //根节点的名字
		var rootRectWidth = 0; //根节点rect的宽度
		var forUpward = true;

		var treeChart = function (d3Object) {
			this.d3 = d3Object;
			this.directions = ['downward'];
		};


		treeChart.prototype.drawChart = function () {
			// First get tree data for both directions.
			this.treeData = {};
			var self = this;
			self.directions.forEach(function (direction) {
				self.treeData[direction] = _this.rootData[direction];
			});
			// rootName = '';
			rootRectWidth = _this.rootName.length * 15;
			self.graphTree(self.getTreeConfig());
		};

		treeChart.prototype.getTreeConfig = function () {
			var treeConfig = {
				'margin': {
					'top': 10,
					'right': 5,
					'bottom': 0,
					'left': 5
				}
			}

			treeConfig.chartWidth = (1600 - treeConfig.margin.right - treeConfig.margin.left);
			treeConfig.chartHeight = (800 - treeConfig.margin.top - treeConfig.margin.bottom);
			treeConfig.centralHeight = treeConfig.chartHeight / 2;
			treeConfig.centralWidth = treeConfig.chartWidth / 6;
			treeConfig.linkLength = 300;
			treeConfig.duration = 500; //动画时间
			return treeConfig;
		};

		treeChart.prototype.graphTree = function (config) {


			var self = this;
			var d3 = this.d3;
			var linkLength = config.linkLength;
			var duration = config.duration;
			var hasChildNodeArr = [];
			var id = 0;
			var diagonal = d3.svg.diagonal()
					.projection(function (d) {
						return [d.y, d.x];
					});
			var pathFunc;
			if (false) {
				pathFunc = diagonal;
			} else {
				pathFunc = funLine;
			}
			var zoom = d3.behavior.zoom()
					.scaleExtent([0.5, 2])
					.on('zoom', redraw);
			var svg = d3.select('#product_tree')
					.append('svg')
					.attr('width', config.chartWidth + config.margin.right + config.margin.left)
					.attr('height', config.chartHeight + config.margin.top + config.margin.bottom)
					.attr('xmlns', 'http://www.w3.org/2000/svg')
					.on('mousedown', disableRightClick)
					.call(zoom)
					.on('dblclick.zoom', null);
			var treeG = svg.append('g')
					.attr('class', 'gbox')
					.attr('transform', 'translate(' + config.margin.left + ',' + config.margin.top + ')');

			//箭头
			var markerDown = svg.append("marker")
					.attr("id", "resolvedDown")
					.attr("markerUnits", "strokeWidth") //设置为strokeWidth箭头会随着线的粗细发生变化
					.attr("markerUnits", "userSpaceOnUse")
					.attr("viewBox", "0 -5 10 10") //坐标系的区域
					.attr("refX", 0) //箭头坐标
					.attr("refY", 0)
					.attr("markerWidth", 12) //标识的大小
					.attr("markerHeight", 12)
					.attr("orient", "0") //绘制方向，可设定为：auto（自动确认方向）和 角度值
					.attr("stroke-width", 2) //箭头宽度
					.append("path")
					.attr("d", "M0,-5L10,0L0,5") //箭头的路径
					.attr('fill', '#000'); //箭头颜色

			// Initialize the tree nodes and update chart.

			for (var d in this.directions) {
				var direction = this.directions[d];
				var data = self.treeData[direction];
				if (typeof (data) != "undefined") {
					data.x0 = config.centralHeight;
					data.y0 = config.centralWidth;
					data.children.forEach(collapse);
					update(data, data, treeG);
				}
			}

			function update(source, originalData, g) {
				var arr = [{
					"name": "begin",
					"value": "2020-01-02"
				},
					{
						"name": "end",
						"value": "2020-01-02"
					}
				]
				var obj = {}
				for (var item in arr) {
					obj[arr[item].name] = arr[item].value;
				};
				//         var datas = arr.map((item) => {

				// return { biginDate: item.value, value: item.value }

				// })
				console.log(obj)
				//         for (var key of Object.keys(arr)) {
				//   console.log(JSON.stringify(key) + ': ' +JSON.stringify(arr[key]) );
				// }
				console.log(source)
				var direction = originalData['direction'];
				var node_class = direction + 'Node';
				var link_class = direction + 'Link';
				var downwardSign = 1;
				var nodeColor = '#8b4513';

				var isExpand = false;
				var nodeSpace = 80;
				var tree = d3.layout.tree().sort(sortByDate).nodeSize([nodeSpace, 0]);
				var nodes = tree.nodes(originalData);
				var links = tree.links(nodes);
				var offsetY = -config.centralHeight;

				nodes.forEach(function (d) {
					d.y = downwardSign * (d.depth * linkLength) + config.centralWidth;
					d.x = d.x - offsetY;
					if (d.name == 'origin') {
						d.x = config.centralHeight;
						d.y += downwardSign * 0; // 左右两树图根节点之间的距离
					}
				});

				// Update the node.
				var node = g.selectAll('g.' + node_class)
						.data(nodes, function (d) {
							return d.id || (d.id = ++id);
						});
				var nodeEnter = node.enter().append('g')

						.attr('class', node_class)
						//			.classed('alink',true)//为选择名称而添加的统一class
						.attr('data-text', function (d) {
							return d.name;
						})
						.attr('transform', function (d) {
							return 'translate(' + source.y0 + ',' + source.x0 + ')';
						})

						.style('cursor', function (d) {
							return (d.name == 'origin') ? '' : (d.children || d._children) ? 'pointer' : '';
						});
				// .on('click', click);


				nodeEnter.append("svg:rect")
						.attr("x", function (d) {
							return (d.name == 'origin') ? -(rootRectWidth / 2) : 95;
						})
						.attr("y", function (d) {
							return (d.name == 'origin') ? -20 : -16;
						})
						.attr("width", function (d) {
							return (d.name == 'origin') ? rootRectWidth : 150;
						})
						.attr("height", 40)
						.attr("rx", 10)
						.style("stroke", function (d) {
							return (d.name == 'origin') ? "#0505ee" : "#CCC"; //外边框颜色
						})
						.style("fill", function (d) {
							return (d.name == 'origin') ? "#0080E3" : "#FFF"; //节点背景色
						});

				nodeEnter.append('circle')
						.attr('r', 1e-6);
				nodeEnter.append("text")
						.attr("class", "linkname")
						.attr("x", function (d) {
							return (d.name == 'origin') ? '0' : '170';
						})
						.attr('dy', function (d) {
							return (d.name == 'origin') ? '.35em' : '5';
						})
						.attr("text-anchor", 'middle')
						.attr('fill', '#337ab7')
						.text(function (d) {
							if (d.name == 'origin') {
								// return ((forUpward) ? '根节点TOP' : '根节点Bottom');
								return _this.rootName;
							}
							if (d.repeated) {
								return '[Recurring] ' + d.name;
							}
							return (d.name.length > 10) ? d.name.substr(0, 10) : d.name;
						})
						.style({
							'fill-opacity': 1e-6,
							'fill': function (d) {
								if (d.name == 'origin') {
									return '#fff';
								}
							},
							'font-size': function (d) {
								return (d.name == 'origin') ? 14 : 13;
							},
							'cursor': "pointer"
						})
						.on('click', Change_modal);

				nodeEnter.append("text")
						.attr("class", "linkname")
						.attr("x", function (d) {
							return (d.name == 'origin') ? '0' : '170';
						})
						.attr("dy", function (d) {
							return (d.name == 'origin') ? '0' : '9';
						})
						.attr("text-anchor", 'middle')
						.text(function (d) {
							return d.name.substr(10, d.name.length);
						})
						.style({
							'fill': '#337ab7',
							'font-size': function (d) {
								return (d.name == 'origin') ? 14 : 13;
							},
							'cursor': "pointer"
						});
				// .on('click', Change_modal);
				nodeEnter.append("text")
						.attr("x", function (d) {
							return (d.name == 'origin') ? '0' : '170';
						})
						.attr("dy", function (d) {
							return (d.name == 'origin') ? '.35em' : '20';
						})
						.attr("text-anchor", 'middle')
						.attr("class", "linkname")
						.style("fill", "#7F7F7F")
						.style('font-size', 10)
						// .text(function (d) {
						// 	var str = (d.name == 'origin') ? '' : "认缴金额:" + d.amount + "万";
						// 	return (str.length > 13) ? str.substr(0, 13) + ".." : str;
						// });
				nodeEnter.append("text")
						.attr("x", "30")
						.attr("dy", function (d) {
							return (d.name == 'origin') ? '.35em' : '-5';
						})
						.attr("text-anchor", "start")
						.attr("class", "linkname")
						.style("fill", "green")
						.style('font-size', 10)
						.text(function (d) {
							return (d.name == 'origin') ? "" : d.ratio;
						});

				nodeEnter.append("text")
						.attr("x", '35')
						.attr("dy", function (d) {
							return (d.name == 'origin') ? '.35em' : '15';
						})
						.attr("text-anchor", "start")
						.attr("class", "linkname")
						.style("fill", "#0ba4ec")
						.style('font-size', 10)
						.text(function (d) {
							// return (d.name == 'origin' || parseFloat(d.ratio.substring(0, 3)) <= 50) ? "" : '控股';
							return d.relType
						});


				// Transition nodes to their new position.原有节点更新到新位置
				var nodeUpdate = node.transition()
						.duration(duration)
						.attr('transform', function (d) {
							return 'translate(' + d.y + ',' + d.x + ')';
						});
				nodeUpdate.select('circle')
						.attr('r', function (d) {
							return (d.name == 'origin') ? 0 : (hasChildNodeArr.indexOf(d) == -1) ? 0 : 6;
						})
						.attr('cx', function (d) {
							return (d.name == 'origin') ? -20 : 251;
						})
						.style('fill', function (d) {
							return hasChildNodeArr.indexOf(d) != -1 ? "#fff" : "";
							// if (d._children || d.children) { return "#fff"; } else { return "rgba(0,0,0,0)"; }
						})
						.style('stroke', function (d) {
							return hasChildNodeArr.indexOf(d) != -1 ? "#8b4513" : "";
							// if (d._children || d.children) { return "#8b4513"; } else { return "rgba(0,0,0,0)"; }
						})
						.style('fill-opacity', function (d) {
							if (d.children) {
								return 0.35;
							}
						})
						// Setting summary node style as class as mass style setting is
						// not compatible to circles.
						.style('stroke-width', function (d) {
							if (d.repeated) {
								return 5;
							}
						});
				//代表是否展开的+-号
				nodeEnter.append("svg:text")
						.attr("class", "isExpand")
						.attr("x", "251")
						.attr("dy", function (d) {
							return 5;
						})
						.attr("text-anchor", "middle")
						.style("fill", "#000")
						.text(function (d) {
							if (d.name == 'origin') {
								return '';
							}
							return hasChildNodeArr.indexOf(d) != -1 ? "+" : "";
							/* if (d._children || d.children) {
                              return "+";
                            } */
						})
						.on('click', click);

				nodeUpdate.select('text').style('fill-opacity', 1);

				var nodeExit = node.exit().transition()
						.duration(duration)
						.attr('transform', function (d) {
							return 'translate(' + source.y + ',' + source.x + ')';
						})
						.remove();
				nodeExit.select('circle')
						.attr('r', 1e-6);
				nodeExit.select('text')
						.style('fill-opacity', 1e-6);

				var link = g.selectAll('path.' + link_class)
						.data(links, function (d) {
							return d.target.id;
						});

				link.enter().insert('path', 'g')
						.attr('class', link_class)
						.attr('stroke', function (d) {
							return '#8b4513';
						})
						.attr('fill', "none")
						.attr('stroke-width', '1px')
						.attr('opacity', 0.5)
						.attr('d', function (d) {
							var o = {
								x: source.x0,
								y: source.y0
							};
							return pathFunc({
								source: o,
								target: o
							});
						})
						.attr("marker-end", function (d) {
							return "url(#resolvedDown)";
						}) //根据箭头标记的id号标记箭头;
						.attr("id", function (d, i) {
							return "mypath" + i;
						});
				link.transition()
						.duration(duration)
						.attr('d', pathFunc);
				link.exit().transition()
						.duration(duration)
						.attr('d', function (d) {
							var o = {
								x: source.x,
								y: source.y
							};
							return pathFunc({
								source: o,
								target: o
							});
						})
						.remove();
				nodes.forEach(function (d) {
					d.x0 = d.x;
					d.y0 = d.y;
				});



				function Change_modal() {
					_this.Modal = true
				}

				function click(d) {
					if (forUpward) {

					} else {
						if (d._children) {
							console.log('对外投资--ok')
						} else {
							console.log('对外投资--no')
						}
					}
					isExpand = !isExpand;
					if (d.name == 'origin') {
						return;
					}
					if (d.children) {
						d._children = d.children;
						d.children = null;
						d3.select(this).text('+')
					} else {
						d.children = d._children;
						d._children = null;
						// expand all if it's the first node
						if (d.name == 'origin') {
							d.children.forEach(expand);
						}
						d3.select(this).text('-')
					}
					update(d, originalData, g);
				}
			}

			function expand(d) {
				if (d._children) {
					d.children = d._children;
					d.children.forEach(expand);
					d._children = null;
				}
			}


			function collapse(d) {
				if (d.children && d.children.length != 0) {
					d._children = d.children;
					d._children.forEach(collapse);
					d.children = null;
					hasChildNodeArr.push(d);
				}
			}


			function redraw() {
				treeG.attr('transform', 'translate(' + d3.event.translate + ')' +
						' scale(' + d3.event.scale + ')');
			}

			function disableRightClick() {
				// stop zoom
				if (d3.event.button == 2) {
					console.log('No right click allowed');
					d3.event.stopImmediatePropagation();
				}
			}


			function sortByDate(a, b) {
				var aNum = a.name.substr(a.name.lastIndexOf('(') + 1, 4);
				var bNum = b.name.substr(b.name.lastIndexOf('(') + 1, 4);
				return d3.ascending(aNum, bNum) ||
						d3.ascending(a.name, b.name) ||
						d3.ascending(a.id, b.id);
			}
		};

		var d3GenerationChart = new treeChart(d3);
		d3GenerationChart.drawChart();

	};
	var funLine = function (obj) { //折线
		var s = obj.source;
		//			console.log('获取折线对象1：'+s.y);
		var sy = (s.name == 'origin' ? s.y + 60 : s.y + 260);
		//			console.log('获取折线对象2：'+sy);
		var t = obj.target;
		var ty = (t.name == 'origin' ? t.y + 90 : t.y + 85);
		return "M" + sy + "," + s.x + "L" + (sy + (ty - sy) / 2) + "," + s.x + "L" + (sy + (ty - sy) / 2) + "," + t.x +
				"L" + ty + "," + t.x;
	}

	var downloadfun = function () { //下载

		// 注释该方法为svg 直接下载
		//  var SvgSaver = require('svgsaver');                 // if using CommonJS environment
		//  var svgsaver = new SvgSaver();                      // creates a new instance
		//  var svg = document.querySelector('#product_tree');         // find the SVG element
		//  svgsaver.asSvg(svg);                                // save as SVG

		var g = document.getElementById('product_tree').getElementsByTagName('g')[0].getBBox();
		var gbox = document.getElementById('product_tree').getElementsByClassName('gbox')[0];
		var x = -g.x; //计算偏移位置
		var y = -g.y;
		gbox.style.transform = "translate(" + x + 'px' + "," + y + "px" + ")  scale(1)"; //偏移位置
		var svgbox = $('#product_tree svg')
		var boxwidth = svgbox.width;
		var boxheight = svgbox.height;
		svgbox.attr('width', g.width)
		svgbox.attr('height', g.height)
		var canvas = document.createElement("canvas");
		var c = canvas.getContext('2d');
		//新建Image对象
		//svg内容
		var svg = document.getElementById('product_tree').innerHTML;
		var img = new Image();

		img.src = 'data:image/svg+xml,' + unescape(encodeURIComponent(svg)); //svg内容中可以有中文字符
		img.src = 'data:image/svg+xml,' + svg; //svg内容中不能有中文字符
		img.src = 'data:image/svg+xml;base64,' + window.btoa(unescape(encodeURIComponent(svg)));
		//图片初始化完成后调用
		img.onload = function () {
			//将canvas的宽高设置为图像的宽高
			canvas.width = img.width;
			canvas.height = img.height + 60;
			c.fillStyle = "#fff";
			c.fillRect(0, 0, canvas.width, canvas.height);
			c.drawImage(img, 0, 30);
			//canvas画图片

			var a = document.createElement("a");
			a.download = "png";
			a.href = canvas.toDataURL("image/png");
			a.click();
		}
		//图片转换为base64后 传给后端 发邮件
		gbox.style.transform = ''
		// svgbox.attr('width',boxwidth)
		// svgbox.attr('height',boxheight)
		svgbox.attr('width', 1100)
		svgbox.attr('height', 600)

		// 结束
	}

	// var margin = {top: 30, right: 10, bottom: 10, left: 10},
	// 		width = 960 - margin.left - margin.right,
	// 		halfWidth = width / 2,
	// 		height = 500 - margin.top - margin.bottom,
	// 		i = 0,
	// 		duration = 500,
	// 		root;
    //
	// var getChildren = function(d){
	// 			var a = [];
	// 			if(d.winners) for(var i = 0; i < d.winners.length; i++){
	// 				d.winners[i].isRight = false;
	// 				d.winners[i].parent = d;
	// 				a.push(d.winners[i]);
	// 			}
	// 			if(d.challengers) for(var i = 0; i < d.challengers.length; i++){
	// 				d.challengers[i].isRight = true;
	// 				d.challengers[i].parent = d;
	// 				a.push(d.challengers[i]);
	// 			}
	// 			return a.length?a:null;
	// 		}
	// ;
    //
	// var tree = d3.layout.tree()
	// 		.size([height, width])
	// ;
    //
	// var diagonal = d3.svg.diagonal()
	// 		.projection(function(d) { return [d.y, d.x]; });
	// var elbow = function (d, i){
	// 	var source = calcLeft(d.source);
	// 	var target = calcLeft(d.target);
	// 	var hy = (target.y-source.y)/2;
	// 	if(d.isRight) hy = -hy;
	// 	return "M" + source.y + "," + source.x
	// 			+ "H" + (source.y+hy)
	// 			+ "V" + target.x + "H" + target.y;
	// };
	// var connector = elbow;
    //
	// var calcLeft = function(d){
	// 	var l = d.y;
	// 	if(!d.isRight){
	// 		l = d.y-halfWidth;
	// 		l = halfWidth - l;
	// 	}
	// 	return {x : d.x, y : l};
	// };
    //
	// var vis = d3.select("#chart").append("svg")
	// 		.attr("width", width + margin.right + margin.left)
	// 		.attr("height", height + margin.top + margin.bottom)
	// 		.append("g")
	// 		.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
    //
	// d3.json("../../static/js/dstes.json", function(json) {
	// 	root = json;
	// 	root.x0 = height / 2;
	// 	root.y0 = width / 2;
    //
	// 	var t1 = d3.layout.tree().size([height, halfWidth]).children(function(d){return d.winners;}),
	// 			t2 = d3.layout.tree().size([height, halfWidth]).children(function(d){return d.challengers;});
	// 	t1.nodes(root);
	// 	t2.nodes(root);
    //
	// 	var rebuildChildren = function(node){
	// 		node.children = getChildren(node);
	// 		if(node.children) node.children.forEach(rebuildChildren);
	// 	}
	// 	rebuildChildren(root);
	// 	root.isRight = false;
	// 	update(root);
	// });
    //
	// var toArray = function(item, arr){
	// 	arr = arr || [];
	// 	var i = 0, l = item.children?item.children.length:0;
	// 	arr.push(item);
	// 	for(; i < l; i++){
	// 		toArray(item.children[i], arr);
	// 	}
	// 	return arr;
	// };
    //
	// function update(source) {
	// 	// Compute the new tree layout.
	// 	var nodes = toArray(source);
    //
	// 	// Normalize for fixed-depth.
	// 	nodes.forEach(function(d) { d.y = d.depth * 180 + halfWidth; });
    //
	// 	// Update the nodes…
	// 	var node = vis.selectAll("g.node")
	// 			.data(nodes, function(d) { return d.id || (d.id = ++i); });
    //
	// 	// Enter any new nodes at the parent's previous position.
	// 	var nodeEnter = node.enter().append("g")
	// 			.attr("class", "node")
	// 			.attr("transform", function(d) { return "translate(" + source.y0 + "," + source.x0 + ")"; })
	// 			.on("click", click);
    //
	// 	nodeEnter.append("circle")
	// 			.attr("r", 1e-6)
	// 			.style("fill", function(d) { return d._children ? "lightsteelblue" : "#fff"; });
    //
	// 	nodeEnter.append("text")
	// 			.attr("dy", function(d) { return d.isRight?14:-8;})
	// 			.attr("text-anchor", "middle")
	// 			.text(function(d) { return d.name; })
	// 			.style("fill-opacity", 1e-6);
    //
	// 	// Transition nodes to their new position.
	// 	var nodeUpdate = node.transition()
	// 			.duration(duration)
	// 			.attr("transform", function(d) { p = calcLeft(d); return "translate(" + p.y + "," + p.x + ")"; })
	// 	;
    //
	// 	nodeUpdate.select("circle")
	// 			.attr("r", 4.5)
	// 			.style("fill", function(d) { return d._children ? "lightsteelblue" : "#fff"; });
    //
	// 	nodeUpdate.select("text")
	// 			.style("fill-opacity", 1);
    //
	// 	// Transition exiting nodes to the parent's new position.
	// 	var nodeExit = node.exit().transition()
	// 			.duration(duration)
	// 			.attr("transform", function(d) { p = calcLeft(d.parent||source); return "translate(" + p.y + "," + p.x + ")"; })
	// 			.remove();
    //
	// 	nodeExit.select("circle")
	// 			.attr("r", 1e-6);
    //
	// 	nodeExit.select("text")
	// 			.style("fill-opacity", 1e-6);
    //
	// 	// Update the links...
	// 	var link = vis.selectAll("path.link")
	// 			.data(tree.links(nodes), function(d) { return d.target.id; });
    //
	// 	// Enter any new links at the parent's previous position.
	// 	link.enter().insert("path", "g")
	// 			.attr("class", "link")
	// 			.attr("d", function(d) {
	// 				var o = {x: source.x0, y: source.y0};
	// 				return connector({source: o, target: o});
	// 			});
    //
	// 	// Transition links to their new position.
	// 	link.transition()
	// 			.duration(duration)
	// 			.attr("d", connector);
    //
	// 	// Transition exiting nodes to the parent's new position.
	// 	link.exit().transition()
	// 			.duration(duration)
	// 			.attr("d", function(d) {
	// 				var o = calcLeft(d.source||source);
	// 				if(d.source.isRight) o.y -= halfWidth - (d.target.y - d.source.y);
	// 				else o.y += halfWidth - (d.target.y - d.source.y);
	// 				return connector({source: o, target: o});
	// 			})
	// 			.remove();
    //
	// 	// Stash the old positions for transition.
	// 	nodes.forEach(function(d) {
	// 		var p = calcLeft(d);
	// 		d.x0 = p.x;
	// 		d.y0 = p.y;
	// 	});
    //
	// 	// Toggle children on click.
	// 	function click(d) {
	// 		if (d.children) {
	// 			d._children = d.children;
	// 			d.children = null;
	// 		} else {
	// 			d.children = d._children;
	// 			d._children = null;
	// 		}
	// 		update(source);
	// 	}
	// }
	// var myChart = echarts.init(document.getElementById('main'));
	// option = {
	// 	title: { text: '人民的名义关系图谱' },
	// 	tooltip: {
	// 		formatter: function (x) {
	// 			return x.data.des;
	// 		}
	// 	},
	// 	series: [
	// 		{
	// 			type: 'graph',
	// 			layout: 'force',
	// 			symbolSize: 80,
	// 			roam: true,
	// 			edgeSymbol: ['circle', 'arrow'],
	// 			edgeSymbolSize: [4, 10],
	// 			edgeLabel: {
	// 				normal: {
	// 					textStyle: {
	// 						fontSize: 20
	// 					}
	// 				}
	// 			},
	// 			force: {
	// 				repulsion: 2500,
	// 				edgeLength: [10, 50]
	// 			},
	// 			draggable: true,
	// 			itemStyle: {
	// 				normal: {
	// 					color: '#4b565b'
	// 				}
	// 			},
	// 			 Style: {
	// 				normal: {
	// 					width: 2,
	// 					color: '#4b565b'
	//
	// 				}
	// 			},
	// 			edgeLabel: {
	// 				normal: {
	// 					show: true,
	// 					formatter: function (x) {
	// 						return x.data.name;
	// 					}
	// 				}
	// 			},
	// 			label: {
	// 				normal: {
	// 					show: true,
	// 					textStyle: {
	// 					}
	// 				}
	// 			},
	// 			data: [
	// 				{
	// 					name: '侯亮平',
	// 					des: '最高检反贪局侦查处处长，汉东省人民检察院副检察长兼反贪局局长。<br/>经过与腐败违法分子的斗争，最终将一批腐败分子送上了审判台，<br/>正义战胜邪恶，自己也迎来了成长。',
	// 					symbolSize: 100,
	// 					itemStyle: {
	// 						normal: {
	// 							color: 'red'
	// 						}
	// 					}
	// 				}, {
	// 					name: '李达康',
	// 					des: '汉东省省委常委，京州市市委书记。是一个正义无私的好官。<br/>但为人过于爱惜自己的羽毛，对待身边的亲人和朋友显得有些无情。',
	// 					itemStyle: {
	// 						normal: {
	// 							color: 'red'
	// 						}
	// 					}
	// 				}, {
	// 					name: '祁同伟',
	// 					des: '汉东省公安厅厅长',
	// 					symbolSize: 100
	// 				}, {
	// 					name: '陈岩石',
	// 					des: '离休干部、汉东省检察院前常务副检察长。充满正义，平凡而普通的共 产 党人。<br/>对大老虎赵立春，以各种形式执着举报了十二年。<br/>在这场关系党和国家生死存亡的斗争中，老人家以耄耋高龄，<br/>义无反顾地背起了炸 药包，在反腐胜利前夕因病去世。',
	// 					itemStyle: {
	// 						normal: {
	// 							color: 'red'
	// 						}
	// 					}
	// 				}, {
	// 					name: '陆亦可',
	// 					des: '汉东省检察院反贪局一处处长',
	// 					itemStyle: {
	// 						normal: {
	// 							color: 'red'
	// 						}
	// 					}
	// 				}, {
	// 					name: '高育良',
	// 					des: '汉东省省委副书记兼政法委书记。年近六十，是一个擅长太极功夫的官场老手。侯亮平、陈海和祁同伟是其学生。',
	// 					symbolSize: 100
	// 				}, {
	// 					name: '沙瑞金',
	// 					des: '汉东省省委书记',
	// 					itemStyle: {
	// 						normal: {
	// 							color: 'red'
	// 						}
	// 					}
	// 				}, {
	// 					name: '高小琴',
	// 					des: '山水集团董事长，是一位叱咤于政界和商界的风云人物，处事圆滑、精明干练。'
	// 				}, {
	// 					name: '高小凤'
	// 				}, {
	// 					name: '赵东来',
	// 					des: '汉东省京州市公安局局长，充满正义，整治恶势力，<br/>在与检察部门的合作中从最初的质疑到之后的通力配合。',
	// 					itemStyle: {
	// 						normal: {
	// 							color: 'red'
	// 						}
	// 					}
	// 				}, {
	// 					name: '程度',
	// 					des: '汉东省京州市公安局光明区分局局长，因犯错误，被李达康书记和赵东来局长点名要清除公安队伍。<br/>但在高小琴的帮助下，祁同伟调他当上了省公安厅办公室副主任。<br/>尽管程度逃避了所有罪责，上面也有人保他，<br/>但最终还是在反贪局局长侯亮平的缜密侦查下被绳之于法。',
	// 				}, {
	// 					name: '吴惠芬',
	// 					des: '汉东省省委副书记高育良的妻子，汉东大学明史教授。',
	// 					itemStyle: {
	// 						normal: {
	// 							color: 'red'
	// 						}
	// 					}
	// 				}, {
	// 					name: '赵瑞龙',
	// 					des: '副国级人物赵立春的公子哥，官二代，打着老子的旗子，<br/>黑白两道通吃，可谓呼风唤雨，权倾一时。',
	// 				}, {
	// 					name: '赵立春',
	// 					des: '副国级领导人，曾经的改革闯将，在权利面前，显得极其渺小。',
	// 				}, {
	// 					name: '陈海',
	// 					itemStyle: {
	// 						normal: {
	// 							color: 'red'
	// 						}
	// 					}
	// 				}, {
	// 					name: '梁璐',
	// 					itemStyle: {
	// 						normal: {
	// 							color: 'red'
	// 						}
	// 					}
	// 				}, {
	// 					name: '刘新建'
	// 				}, {
	// 					name: '欧阳菁'
	// 				}, {
	// 					name: '吴心怡',
	// 					itemStyle: {
	// 						normal: {
	// 							color: 'red'
	// 						}
	// 					}
	// 				}, {
	// 					name: '蔡成功'
	// 				}, {
	// 					name: '丁义珍'
	// 				}
	// 			],
	// 			links: [
	// 				{
	// 					source: '高育良',
	// 					target: '侯亮平',
	// 					name: '师生',
	// 					des: '侯亮平是高育良的得意门生'
	// 				}, {
	// 					source: '高育良',
	// 					target: '祁同伟',
	// 					name: '师生'
	// 				}, {
	// 					source: '赵立春',
	// 					target: '高育良',
	// 					name: "前领导"
	// 				}, {
	// 					source: '赵立春',
	// 					target: '赵瑞龙',
	// 					name: "父子"
	// 				}, {
	// 					source: '赵立春',
	// 					target: '刘新建',
	// 					name: "前部队下属"
	// 				}, {
	// 					source: '李达康',
	// 					target: '赵立春',
	// 					name: "前任秘书"
	// 				}, {
	// 					source: '祁同伟',
	// 					target: '高小琴',
	// 					name: "情人"
	// 				}, {
	// 					source: '陈岩石',
	// 					target: '陈海',
	// 					name: "父子"
	// 				}, {
	// 					source: '陆亦可',
	// 					target: '陈海',
	// 					name: "属下"
	// 				}, {
	// 					source: '沙瑞金',
	// 					target: '陈岩石',
	// 					name: "故人"
	// 				}, {
	// 					source: '祁同伟',
	// 					target: '陈海',
	// 					name: "师哥"
	// 				}, {
	// 					source: '祁同伟',
	// 					target: '侯亮平',
	// 					name: "师哥"
	// 				}, {
	// 					source: '侯亮平',
	// 					target: '陈海',
	// 					name: "同学，生死朋友"
	// 				}, {
	// 					source: '高育良',
	// 					target: '吴惠芬',
	// 					name: "夫妻"
	// 				}, {
	// 					source: '陈海',
	// 					target: '赵东来',
	// 					name: "朋友"
	// 				}, {
	// 					source: '高小琴',
	// 					target: '高小凤',
	// 					name: "双胞胎",
	// 					symbolSize: '1'
	// 				}, {
	// 					source: '赵东来',
	// 					target: '陆亦可',
	// 					name: "爱慕"
	// 				}, {
	// 					source: '祁同伟',
	// 					target: '程度',
	// 					name: "领导"
	// 				}, {
	// 					source: '高育良',
	// 					target: '高小凤',
	// 					name: "情人"
	// 				}, {
	// 					source: '赵瑞龙',
	// 					target: '祁同伟',
	// 					name: "勾结",
	// 					symbolSize: '1',
	// 				}, {
	// 					source: '李达康',
	// 					target: '赵东来',
	// 					name: "领导"
	// 				}, {
	// 					source: '赵瑞龙',
	// 					target: '程度',
	// 					name: "领导"
	// 				}, {
	// 					source: '沙瑞金',
	// 					target: '李达康',
	// 					name: "领导"
	// 				}, {
	// 					source: '沙瑞金',
	// 					target: '高育良',
	// 					name: "领导"
	// 				}, {
	// 					source: '祁同伟',
	// 					target: '梁璐',
	// 					name: "夫妻"
	// 				}, {
	// 					source: '吴惠芬',
	// 					target: '梁璐',
	// 					name: "朋友"
	// 				}, {
	// 					source: '李达康',
	// 					target: '欧阳菁',
	// 					name: "夫妻"
	// 				}, {
	// 					source: '赵瑞龙',
	// 					target: '刘新建',
	// 					name: "洗钱工具"
	// 				}, {
	// 					source: '赵瑞龙',
	// 					target: '高小琴',
	// 					name: "勾结，腐化",
	// 					symbolSize: '1'
	// 				}, {
	// 					source: '赵瑞龙',
	// 					target: '高小凤',
	// 					name: "腐化"
	// 				}, {
	// 					source: '吴心怡',
	// 					target: '陆亦可',
	// 					name: "母女"
	// 				}, {
	// 					source: '吴心怡',
	// 					target: '吴惠芬',
	// 					name: "姐妹"
	// 				}, {
	// 					source: '蔡成功',
	// 					target: '侯亮平',
	// 					name: "发小"
	// 				}, {
	// 					source: '蔡成功',
	// 					target: '欧阳菁',
	// 					name: "举报",
	// 					lineStyle: {
	// 						normal: {
	// 							type: 'dotted',
	// 							color: '#000'
	// 						}
	// 					}
	// 				}, {
	// 					source: '欧阳菁',
	// 					target: '刘新建',
	// 					name: "举报",
	// 					lineStyle: {
	// 						normal: {
	// 							type: 'dotted',
	// 							color: '#000'
	// 						}
	// 					}
	// 				}, {
	// 					source: '刘新建',
	// 					target: '赵瑞龙',
	// 					name: "举报",
	// 					lineStyle: {
	// 						normal: {
	// 							type: 'dotted',
	// 							color: '#000'
	// 						}
	// 					}
	// 				}, {
	// 					source: '李达康',
	// 					target: '丁义珍',
	// 					name: "领导"
	// 				}, {
	// 					source: '高小琴',
	// 					target: '丁义珍',
	// 					name: "策划出逃"
	// 				}, {
	// 					source: '祁同伟',
	// 					target: '丁义珍',
	// 					name: "勾结"
	// 				}, {
	// 					source: '赵瑞龙',
	// 					target: '丁义珍',
	// 					name: "勾结"
	// 				}]
	// 		}
	// 	]
	// };
	// myChart.setOption(option);
var unitCode = undefined;

function onClick(event, treeId, treeNode){
	unitCode = treeNode.unitCode
}
function addStaff(){
	if(unitCode != undefined)
	{
		$.ajax({
			type: 'GET',
			url: '${base}/sys/unit/isOrg?unitCode=' + unitCode,
			dataType: 'json',
			success: function(data){
				if(data.valid)
				{
					$.messager.toastr('提示', '请选择具体的部门添加用户');
				}
				else
				{
					window.location = '${base}/sys/staff/add?deptCode=' + unitCode;
				}
			}
		});
	}
	else
	{
		$.messager.toastr('提示', '请先选择机构');
	}
}

/* 设定角色 */
function setRole(){
	var row = $('#staffTable').bootstrapTable('getSelections');
	if(row.length != 0)
	{
		window.location = '${base}/sys/staff/setRoleStaff?unitCode=' + unitCode + '&staffCode=' + row[0].staffCode;
	}
	else
	{
		$.messager.toastr('提示','请选择一个用户');
	}
}

/* 设定角色组*/
function setRoleGroup(){
	var row = $('#staffTable').bootstrapTable('getSelections');
	if(row.length != 0)
	{
		window.location = '${base}/sys/staff/setRoleGroupStaff?unitCode=' + unitCode + '&staffCode=' + row[0].staffCode;
	}
	else
	{
		$.messager.toastr('提示','请选择一个用户');
	}
}

/*加入用户组*/
function setStaffGroup(){
	var row = $('#staffTable').bootstrapTable('getSelections');
	if(row.length != 0)
	{
		window.location = '${base}/sys/staffGroupMap/setStaffGroup?staffCode=' + row[0].staffCode;
	}
	else
	{
		$.messager.toastr('提示','请选择一个用户');
	}
}

function setProxy(){
	var row = $('#staffTable').bootstrapTable('getSelections');
	if(row.length == 0)
	{
		$.messager.toastr('提示','请选择一个用户');
	}
	else
	{
		window.location = '${base}/sys/staffProxyLog/list?staffCode=' + row[0].staffCode;
		/* $.messager.modal({
			winId: 'winSecondment',
			width: 600,
			height: 300,
			title: '选择借调部门',
			url: '${base}/sys/staff/secondment',
			args: {
				'staffCode': row[0].staffCode,
				'deptCode': unitCode
			},
			buttons: [
				{
					text : '关闭',
					icon : 'times',
					type : 'danger',
					value : false
				}
			],
			callback: result,
			onClose: function(){
				$('#staffTable').bootstrapTable('refresh');
			}
		}); */
	}
}

function result(){
}

</script>
</body>
</html>