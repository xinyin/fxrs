{
"BODY":{
            "failTransList":[<#list failTransList as p>
             {
                 "sourceSysCode":"${p.sourceSysCode}"
             }
            </#list>]

        },
"SYSHEAD":{
"msg":"${msg}",
"resultCode":"${resultCode}",
"TRANS_CODE":"${TRANS_CODE}",
"CHNL_ID":"${CHNL_ID}",
}
}