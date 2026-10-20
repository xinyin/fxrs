<#macro xmlRoot>
<#include "${_TRAN_CODE?trim}.ftl" />
</#macro>

<#function comma index total><#if "${index}" != "${total}"><#return ","></#if><#return ""></#function>
<#setting number_format="0">