{
"BODY":{
"relList":[<#list relList as p>
    {
    "relType":"${p.relType}",
    "relName":"${p.relName}",
    "relUscType":"${p.relUscType}",
    "relUscCode":"${p.relUscCode}"
    }
</#list>]

},
"SYSHEAD":{
"msg":"${msg}",
"resultCode":"${resultCode}",
"TRANS_CODE":"${TRANS_CODE}",
"CHNL_ID":"${CHNL_ID}"
}
}