var TimeUtil = {
    timeData: function (t) {
        var d = new Array();
        for (var i = 0; i < t; i++) {
            if (i == 0)
                d.push({"id": "" + i, "text": "0" + i, "selected": true});
            else if (i < 10)
                d.push({"id": "" + i, "text": "0" + i});
            else
                d.push({"id": "" + i, "text": "" + i});
        }
        return d;
    }, minutes: function (m) {
        return "0 0/" + m + " * 1/1 * ? *";
    }, minutesNext: function (d, arg) {
        if (arg > 60) arg = 60;
        var m = d.getMinutes();
        var a = parseInt(arg);
        if (m < a) {
            d.setMinutes(a);
        } else {
            if (m + a > 59) {
                d.setHours(d.getHours() + 1);
                d.setMinutes(0);
            } else {
                d.setMinutes(m + a);
            }
        }
        d.setSeconds(0);
        return this.formatter(d);
    }, hours: function (f, h, m) {
        if (f == "0") {
            return "0 0 0/" + h + " 1/1 * ? *";
        } else {
            return "0 " + m + " " + h + " 1/1 * ? *";
        }
    }, days: function (f, h, m, d) {
        if (f == "0") {
            return "0 " + m + " " + h + " 1/" + d + " * ? *";
        } else {
            return "0 " + m + " " + h + " ? * MON-FRI * ";
        }
    }, months: function (f, h, m, mon, d) {
        if (f == "0") {
            return "0 " + m + " " + h + " " + d + " 1/" + mon + " ? *";
        } else {
            return "0 " + m + " " + h + " ? 1/" + mon + " " + d + " *";
        }
    }, years: function (f, h, m, mon, d) {
        if (f == "0") {
            return "0 " + m + " " + h + " " + d + " " + mon + " ? *";
        } else {
            return "0 " + m + " " + h + " ? " + mon + " " + d + " *";
        }
    }, weeks: function (h, m, w) {
        return "0 " + m + " " + h + " ? * " + w + " *";
    }, formatter: function (_1b) {
        var y = _1b.getFullYear();
        var m = _1b.getMonth() + 1;
        var d = _1b.getDate();
        var h = _1b.getHours();
        var Min = _1b.getMinutes();
        var s = _1b.getSeconds();

        function _23(_24) {
            return (_24 < 10 ? "0" : "") + _24;
        };
        return y + "-" + _23(m) + "-" + _23(d) + " " + _23(h) + ":" + _23(Min) + ":" + _23(s);
    }, formatterLong: function (value) {
        this.formatter(new Date(value));
    },
    parser: function (s) {
        if ($.trim(s) == "") {
            return new Date();
        }
        var dt = s.split(" ");

        function df(s) {
            var t = Date.parse(s);
            if (!isNaN(t)) {
                return new Date(t);
            } else {
                return new Date();
            }
        }

        var d = df(dt[0]);
        var tt = dt[1].split(":");
        var _25 = parseInt(tt[0], 10);
        var _26 = parseInt(tt[1], 10);
        var _27 = parseInt(tt[2], 10);
        return new Date(d.getFullYear(), d.getMonth(), d.getDate(), _25, _26, _27);
    }, isInt: function (str) {
        var reg = /^(-|\+)?\d+$/;
        return reg.test(str);
    }
};  
