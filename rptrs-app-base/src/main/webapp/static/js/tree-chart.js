(function (d3Instance) {

  var isCommonjs = typeof module !== 'undefined' && module.exports;

  var hasChildNodeArr = [];

  var TreeChart = function (selector) {
    $(selector).html = "";
    this.rootRectWidth = 0;
    this.sourceData = {};
    this.drawId = selector
  };

  TreeChart.prototype.init = function (data, options) {
    this.sourceData = data

    let rootStyle = {
      color: '#fff',
      borderColor: '#0080E3',
      borderWidth: 1,
      backgroundColor: '#0080E3',
      fontSize: 14,
    }
    let nodeStyle = {
      color: '#000',
      borderColor: '#0080E3',
      borderWidth: 1,
      backgroundColor: '#fff',
      fontSize: 11,
    }

    if (options.rootStyle) {
      rootStyle = Object.assign(rootStyle, options.rootStyle)
      delete options.rootStyle
    }
    if (options.nodeStyle) {
      nodeStyle = Object.assign(nodeStyle, options.nodeStyle)
      delete options.nodeStyle
    }

    const defaultOption = {
      rotioLength: 8,
      showNoArrow: false,
      nodeStyle,
      rootStyle
    }

    this.options = Object.assign({}, defaultOption, options)
    this.drawChart()
  };

  TreeChart.prototype.drawChart = function () {
    this.treeData = this.sourceData.directionData || {}
    this.rootRectWidth = this.sourceData["rootName"].length * 15;
    this.graphTree(this.getTreeConfig());
  };

  TreeChart.prototype.getTreeConfig = function () {
    var defaultMargin = {
      top: 10,
      right: 5,
      bottom: 10,
      left: 5,
    }
    var screenWidth = $(this.drawId).width();
    var screenHeight = $(this.drawId).height();
    var chartWidth = screenWidth - defaultMargin.right - defaultMargin.left;
    var chartHeight = screenHeight - defaultMargin.top - defaultMargin.bottom;
    var centralHeight = chartHeight / 2;
    var centralWidth = chartWidth / 2;

    return {
      margin: defaultMargin,
      screenWidth,
      screenHeight,
      chartWidth,
      chartHeight,
      centralHeight,
      centralWidth,
      duration: 500,
      childNodelWidth: 120,
      nodelHeight: 50
    }
  };

  TreeChart.prototype.graphTree = function (config) {
    var _this = this;
    var duration = config.duration;
    var id = 0;

    var zoom = d3Instance.behavior.zoom().scaleExtent([0.5, 3]).on("zoom", redraw);
    var svg = d3Instance.select(this.drawId)
        .append("svg")
        .attr("width", config.chartWidth)
        .attr("height", config.chartHeight)
        .attr("xmlns", "http://www.w3.org/2000/svg")
        .on("mousedown", disableRightClick)
        .call(zoom)
        .on("dblclick.zoom", null);
    var treeG = svg.append("g")
        .attr("class", "gbox")
        .attr("transform", "translate(" + config.margin.left + "," + config.margin.top + ")");

    //右边箭头
    svg.append("marker")
        .attr("id", "arrow-right")
        .attr("markerUnits", "strokeWidth")
        .attr("markerUnits", "userSpaceOnUse")
        .attr("viewBox", "0 -5 10 10")
        .attr("refX", 10)
        .attr("refY", 0)
        .attr("markerWidth", 10)
        .attr("markerHeight", 10)
        .attr("orient", "0")
        .attr("stroke-width", 2)
        .append("path")
        .attr("d", "M0,-5 L10,0 L0,5")
        .attr("fill", "#0080E3");

    //左边箭头（连接线中心位置）
    svg.append("marker")
        .attr("id", "arrow-left")
        .attr("markerUnits", "strokeWidth")
        .attr("markerUnits", "userSpaceOnUse")
        .attr("viewBox", "0 -5 10 10")
        .attr("refX", -50)
        .attr("refY", 0)
        .attr("markerWidth", 10)
        .attr("markerHeight", 10)
        .attr("orient", "0")
        .attr("stroke-width", 2)
        .append("path")
        .attr("d", "M0,-5 L10,0 L0,5")
        .attr("fill", "#0080E3");

    //箭头(连接线最右端)
    svg.append("marker")
        .attr("id", "arrow-left-end")
        .attr("markerUnits", "strokeWidth")
        .attr("markerUnits", "userSpaceOnUse")
        .attr("viewBox", "0 -5 10 10")
        .attr("refX", -100)
        .attr("refY", 0)
        .attr("markerWidth", 10)
        .attr("markerHeight", 10)
        .attr("orient", "0")
        .attr("stroke-width", 2)
        .append("path")
        .attr("d", "M0,-5 L10,0 L0,5")
        .attr("fill", "#0080E3");

    for (var key in this.treeData) {
      var data = this.treeData[key];
      data.x0 = config.centralWidth;
      data.y0 = config.centralHeight;
      data.children.forEach(collapse);
      data.direction = key;
      data.name = 'origin';
      data.nodeStyle = this.sourceData.nodeStyle ? Object.assign({}, this.options.rootStyle, this.sourceData.nodeStyle) : _this.options.rootStyle
      update(data, data, treeG);
    }

    function update(source, originalData, treeG) {
      var direction = originalData["direction"];
      var rightWard = direction == "right";
      var rightSign = rightWard ? 1 : -1;
      var nodeClass = direction + "Node";
      var linkClass = direction + "Link";
      var isExpand = false;
      var hNodeSpace = config.childNodelWidth + 10;
      var horizontalNodeSpace = (config.childNodelWidth + _this.rootRectWidth) / 2 + 120;
      var tree = d3Instance.layout.tree().sort(sortByDate).nodeSize([hNodeSpace, 0]).separation(function (a, b) {
        if (a.parent && b.parent && a.parent.name == 'origin' && b.parent.name == 'origin') {
          return 1
        }
        return 0.5
      });
      var nodes = tree.nodes(originalData);
      var links = tree.links(nodes);

      nodes.forEach(function (d) {
        if (isOrigin(d.name)) {
          d.x = config.centralWidth;
          d.y = config.centralHeight;
          return
        }

        //hNodeSpace产生的每个节点x坐标，用来生成子节点的y坐标
        d.y = config.centralHeight + d.x;
        if (d.parent && d.parent.name == 'origin') {
          d.x = config.centralWidth + rightSign * (d.depth * horizontalNodeSpace);
        } else {
          d.x = config.centralWidth + rightSign * (d.depth * horizontalNodeSpace) + rightSign * (d.depth - 1) * (config.childNodelWidth - _this.rootRectWidth) / 2;
        }
      });

      var node = treeG.selectAll("g." + nodeClass).data(nodes, function (d) { return d.id || (d.id = ++id) });
      var nodeEnter = node.enter()
          .append("g")
          .attr("class", nodeClass)
          .attr("transform", function () { return "translate(" + source.x0 + "," + source.y0 + ")"; })
          .style("cursor", function (d) {
            return isOrigin(d.name) ? "" : d.children || d._children ? "pointer" : "";
          });

      //子节点矩形
      nodeEnter.append("svg:rect")
          .attr("x", function (d) { return isOrigin(d.name) ? -(_this.rootRectWidth / 2) : -config.childNodelWidth / 2; })
          .attr("y", 0)
          .attr("width", function (d) { return isOrigin(d.name) ? _this.rootRectWidth : config.childNodelWidth; })
          .attr("height", config.nodelHeight)
          .attr("rx", 5)
          .style("stroke", function (d) { return d.nodeStyle.borderColor })
          .attr("stroke-width", function (d) { return d.nodeStyle.borderWidth })
          .style("fill", function (d) { return d.nodeStyle.backgroundColor })
          .on("click", clickNode)
          .on("dblclick", dbClickNode)
          .on('mouseover', onMouseOver)
          .on('mouseout', onMouseOut)

      //展开符号  圆
      nodeEnter.append("circle").attr("r", 1e-6)

      //节点公司名称(第一行，显示10个文字)
      nodeEnter.append("text")
          .attr("class", "linkname")
          .attr("x", '0')
          .attr("dy", function (d) { return isOrigin(d.name) ? "28" : "23" })
          .attr("text-anchor", 'middle')
          .attr("fill", function (d) { return d.nodeStyle.color })
          .text(function (d) {
            if (isOrigin(d.name)) {
              return _this.sourceData["rootName"];
            }
            if (d.repeated) {
              return "[Recurring] " + d.name;
            }
            return d.name.length > 10 ? d.name.substr(0, 10) : d.name;
          })
          .style({
            "fill-opacity": 1e-6,
            "font-size": function (d) { return d.nodeStyle.fontSize },
            cursor: "pointer",
          })
          .on("click", clickNode)
          .on("dblclick", dbClickNode)
          .on('mouseover', onMouseOver)
          .on('mouseout', onMouseOut)

      //节点公司名称(第二行，显示剩余文字)
      nodeEnter.append("text")
          .attr("class", "linkname")
          .attr("x", "0")
          .attr("dy", function (d) {
            return isOrigin(d.name) ? ".35em" : "36";
          })
          .attr("text-anchor", "middle")
          .text(function (d) {

            var textLineTwo = d.name.substr(10, d.name.length)
            /**字数太多使用省略号展示*/
            if (textLineTwo.length > 10) {
              if (d._children && d._children.length) {
                textLineTwo = d.name.substr(10, 6) + "..."
              } else {
                textLineTwo = d.name.substr(10, 8) + "..."
              }
            }
            if (d._children && d._children.length) {
              textLineTwo += "(" + d._children.length + ")"
            }
            return textLineTwo
          })
          .style({
            fill: "#000",
            "font-size": function (d) {
              return isOrigin(d.name) ? 14 : 11;
            },
            cursor: "pointer",
          })
          .on("click", clickNode)
          .on("dblclick", dbClickNode)
          .on('mouseover', onMouseOver)
          .on('mouseout', onMouseOut)

      //股权比例(第一行显示4个字符)
      nodeEnter.append("text")
          .attr("x", -rightSign * (config.childNodelWidth / 2 + 10))
          .attr("dy", function (d) {
            return isOrigin(d.name) ? ".35em" : config.nodelHeight / 2 - 2;
          })
          .attr("text-anchor", rightWard ? "end" : "start")
          .attr("class", "linkname")
          .style("fill", "#0080E3")
          .style("font-size", 10)
          .text(function (d) {
            return isOrigin(d.name) ? "" : d.ratio && d.ratio.substring(0, _this.options.rotioLength);
          });

      //股权比例(第二行显示剩余字符)
      nodeEnter.append("text")
          .attr("x", -rightSign * (config.childNodelWidth / 2 + 10))
          .attr("dy", function (d) {
            return isOrigin(d.name) ? ".35em" : config.nodelHeight / 2 + 10;
          })
          .attr("text-anchor", rightWard ? "end" : "start")
          .attr("class", "linkname")
          .style("fill", "#0080E3")
          .style("font-size", 10)
          .text(function (d) {
            return isOrigin(d.name) ? "" : d.ratio && d.ratio.substring(_this.options.rotioLength);
          });

      //原有节点更新到新位置
      var nodeUpdate = node.transition()
          .duration(duration)
          .attr("transform", function (d) {
            return "translate(" + d.x + "," + d.y + ")";
          });

      //反向箭头
      nodeEnter.append("path")
          .attr("transform", function () {
            var transformx = -rightSign * config.childNodelWidth / 2
            var transformY = config.nodelHeight / 2
            return "translate(" + transformx + "," + transformY + ")"
          })
          .attr("d", rightWard ? "M0,0 L-10,5 -10,-5" : "M10,-5 L10,5 0,0 ")
          .style("fill", "#0080E3")
          .attr("orient", "90")
          .style("display", function (d) {
            return d.name == 'origin' ? "none" : d.mshMark ? "block" : "none";
          });


      //子树展开 修改圆圈位置
      nodeUpdate.select("circle")
          .attr("r", function (d) {
            return isOrigin(d.name)
                ? 0
                : hasChildNodeArr.indexOf(d) == -1
                    ? 0
                    : 6;
          })
          .attr("cx", rightSign * (config.childNodelWidth / 2 + 7))
          .attr("cy", function (d) {
            return isOrigin(d.name) ? 0 : config.nodelHeight / 2;
          })
          .style("fill", function (d) {
            return hasChildNodeArr.indexOf(d) != -1 ? "#fff" : "";
          })
          .style("stroke", function (d) {
            return hasChildNodeArr.indexOf(d) != -1 ? "#0080E3" : "";
          })
          .style("fill-opacity", function (d) {
            if (d.children) {
              return 1;
            }
          })
          .style("stroke-width", function (d) {
            if (d.repeated) {
              return 5;
            }
          });

      //代表是否展开的+-号
      nodeEnter.append("svg:text")
          .attr("class", "isExpand")
          .attr("x", rightSign * (config.childNodelWidth / 2 + 7))
          .attr("dy", function (d) {
            return isOrigin(d.name) ? 0 : config.nodelHeight / 2 + 5;
          })
          .attr("text-anchor", "middle")
          .style("fill", "#3399ff")
          .text(function (d) {
            if (isOrigin(d.name)) {
              return "";
            }
            return hasChildNodeArr.indexOf(d) != -1 ? "+" : "";
          })
          .on("click", expandNodes);

      nodeUpdate.select("text").style("fill-opacity", 1);

      var nodeExit = node.exit()
          .transition()
          .duration(duration)
          .attr("transform", function (d) {
            return "translate(" + source.x + "," + source.y + ")";
          })
          .remove();

      nodeExit.select("circle").attr("r", 1e-6);
      nodeExit.select("text").style("fill-opacity", 1e-6);

      var link = treeG.selectAll("path." + linkClass)
          .data(links, function (d) {
            return d.target.id;
          });

      //连接线
      link.enter()
          .insert("path", "g")
          .attr("class", linkClass)
          .attr("d", function (d) {
            var o = {
              x: source.x0,
              y: source.y0,
            };
            return elbow({
              source: o,
              target: o
            }, d);
          })
          .attr("marker-end", function (d) {
            if (_this.options.showNoArrow) return;
            return rightWard ? "url(#arrow-right)" : d.source.children.length === 1 ? "url(#arrow-left-end)" : "url(#arrow-left)";
          })
          .attr("id", function (d, i) {
            return "mypath" + i;
          })
          .attr("fill", "none")
          .attr("stroke", "#000")
          .attr("stroke-width", "1px")
          .attr("opacity", 1);

      link.transition()
          .duration(duration)
          .attr("d", function (d) {
            return elbow(d, d);
          });

      link.exit()
          .transition()
          .duration(duration)
          .attr("d", function (d) {
            var o = {
              x: source.x,
              y: source.y,
            };
            return elbow({
              source: o,
              target: o,
            }, d);
          })
          .remove();

      nodes.forEach(function (d) {
        d.x0 = d.x;
        d.y0 = d.y;
      });

      /**
       * @description: 生成连接线
       * @param {*} d link对象，包含souce和target
       * @param {*} target link对象的target
       */
      function elbow(d, link) {
        var sourceX = d.source.name == "origin"
            ? d.source.x + rightSign * _this.rootRectWidth / 2
            : d.source.x + rightSign * config.childNodelWidth / 2
        var sourceY = d.source.y + config.nodelHeight / 2
        var targetX = d.target.x - rightSign * config.childNodelWidth / 2
        var targetY = d.target.y + config.nodelHeight / 2;

        var sourceChildren = link.source.children
        if (!sourceChildren) return
        var index = sourceChildren.indexOf(link.target)

        //1、首个子元素展示完整link路径
        //2、末尾子元素从父子一半间距位置开始展示link路径
        //3、其余子元素子展示子元素到一半间距的横线link路径
        if (index === 0) {
          return (
              "M" +
              sourceX +
              "," +
              sourceY +
              "H" +
              ((targetX - sourceX) / 2 + sourceX) +
              "V" +
              targetY +
              "H" +
              targetX
          )
        } else if (index === sourceChildren.length - 1) {
          return (
              "M" +
              ((targetX - sourceX) / 2 + sourceX) +
              "," +
              sourceY +
              "V" +
              targetY +
              "H" +
              targetX
          )
        } else {
          return (
              "M" +
              ((targetX - sourceX) / 2 + sourceX) +
              "," +
              targetY +
              "H" +
              targetX
          )
        }
      }

      function expandNodes(d) {
        isExpand = !isExpand;
        if (isOrigin(d.name)) {
          return;
        }

        if (d.children && !d._childrenBak) {
          d._children = d.children;
          d.children = null;
          d3Instance.select(this).text("+");
        } else {
          if (d._childrenBak) {
            d.children = d._childrenBak;
            delete d._childrenBak;
          } else {
            d.children = d._children;
          }
          d._children = null;
          if (isOrigin(d.name)) {
            d.children.forEach(expand);
          }
          d3Instance.select(this).text("-");
          d3Instance.select(this).attr("id", d.id);
        }

        update(d, originalData, treeG);
      }

      function clickNode(d) {
        if (_this.options.onClick) {
          _this.options.onClick(d)
        }
      }

      function dbClickNode(d) {
        if (_this.options.onDoubleClick) {
          _this.options.onDoubleClick(d)
        }
      }

      function onMouseOver(d, i) {
        var current = nodeEnter[0][i]
        var currentNode = d3Instance.select(current)

        currentNode.append("svg:rect")
            .attr("x", function (d) { return isOrigin(d.name) ? _this.rootRectWidth / 2 : config.childNodelWidth / 2 })
            .attr("y", config.nodelHeight - 10)
            .attr("width", function (d) {
              nodeName = isOrigin(d.name) ? _this.sourceData.rootName : d.name
              return d.nodeStyle.fontSize * nodeName.length + 4
            })
            .attr("height", function (d) { return d.nodeStyle.fontSize + 6 })
            .style("stroke", function (d) { return '#ddd' })
            .attr("stroke-width", function (d) { return 1 })
            .style("fill", function (d) { return '#f5f5f5' })
            .attr('class', 'hover-text')

        currentNode.append('text')
            .attr('x', function (d) { return isOrigin(d.name) ? _this.rootRectWidth / 2 + 2 : config.childNodelWidth / 2 + 2 })
            .attr('y', config.nodelHeight + 2)
            .attr('font-family', 'sans-serif')
            .attr('font-size', 10)
            .attr('text-anchor', 'front')
            .attr('class', 'hover-text')
            .text(function (d) {
              nodeName = isOrigin(d.name) ? _this.sourceData.rootName : d.name
              /**显示全部名称*/
              return  nodeName
            });
      }

      function onMouseOut() {
        d3.selectAll('.hover-text').remove();
      }

    }



    function redraw() {
      treeG.attr(
          "transform",
          "translate(" +
          d3Instance.event.translate +
          ")" +
          " scale(" +
          d3Instance.event.scale +
          ")"
      )
    }

    function collapse(d) {
      d.nodeStyle = d.nodeStyle ? Object.assign({}, _this.options.nodeStyle, d.nodeStyle) : _this.options.nodeStyle

      if (d.children && d.children.length) {
        d._children = d.children;
        d._children.forEach(collapse);
        d.children = null;
        hasChildNodeArr.push(d);
      }
    }
  };

  function isOrigin(name) {
    return name == 'origin'
  }

  function expand(d) {
    if (d._children) {
      d.children = d._children;
      d.children.forEach(expand);
      d._children = null;
    }
  }

  function disableRightClick() {
    if (d3Instance.event.button == 2) {
      console.log("No right click allowed");
      d3Instance.event.stopImmediatePropagation();
    }
  }

  function sortByDate(a, b) {
    var aNum = a.name.substr(a.name.lastIndexOf("(") + 1, 4);
    var bNum = b.name.substr(b.name.lastIndexOf("(") + 1, 4);
    /**节点排序，返回数据已按后台顺序排序，此处无需定义规则*/
    return 0;
    /*return (
      d3Instance.ascending(aNum, bNum) ||
      d3Instance.ascending(a.name, b.name) ||
      d3Instance.ascending(a.id, b.id)
    )*/
  }

  if (isCommonjs) {
    module.exports = TreeChart;
  } else {
    window.TreeChart = TreeChart;
  }
})(d3)