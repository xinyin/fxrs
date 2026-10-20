{
"BODY":{
        "relatedList":[<#list relatedList as p>
                        {
                        "name":"${p.name}",
                        "type":"${p.type}",
                        "identifyType":"${p.identifyType}",
                        "identifyNumber":"${p.identifyNumber}"
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