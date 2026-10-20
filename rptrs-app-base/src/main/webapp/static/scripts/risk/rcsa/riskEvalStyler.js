var evalMapData;

$.ajax({
    url: contextPath + '/rcsa/riskMap/getUnitRiskMapDto.json',
    type: 'post',
    async: false,
    data: {unitCode: ''},
    success: function (data) {
        if (data) evalMapData = data;
    }
});

function fixedRiskSeverityStyler(value, row) {
    var _freq = row.fixedRiskFreq;
    var _fin = row.fixedRiskFinance;
    var _cus = row.fixedRiskCustomer;
    var _ren = row.fixedRiskRenown;
    var _sup = row.fixedRiskSupervise;
    var _emp = row.fixedRiskEmployee;

    if (evalMapData == null || !_freq || (!_fin && !_cus && !_ren && !_sup && !_emp)) {
        return 'background:#FFFFFF';
    }

    var _arr = [];
    if (_fin) _arr.push(_fin);
    if (_cus) _arr.push(_cus);
    if (_ren) _arr.push(_ren);
    if (_sup) _arr.push(_sup);
    if (_emp) _arr.push(_emp);
    $.sort.insertSort(_arr);


    var _val = _arr.pop();
    for (var i = 0; i < evalMapData.length; i++) {
        var _m = evalMapData[i];
        if (_m.riskFreq == _freq && _m.riskFinance == _val) {
            return 'background:' + _m.rgb;
        }
    }

    return 'background:#FFFFFF';

};