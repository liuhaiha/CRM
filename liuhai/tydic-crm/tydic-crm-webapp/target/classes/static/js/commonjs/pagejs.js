/*************************
 * 
 * 使用方式： 
 * 1.在界面html 加入 <tfoot id="pageDiv" align="right"></tfoot>
 * 2.在html 引入../commonjs/pagejs.js
 * 
 * ***********************
 */

$(document).ready(function(){
	$("#pageDiv_pro").load("/crm/view/page/page_pro.html");
	$("#pageDiv").load("/crm/view/page/page_linkman.html");
	$("#pageDiv_meeting").load("/crm/view/page/page_meeting.html");
	$("#page_log").load("/crm/view/page/page_log.html");
	$("#pageDiv_list").load("/crm/view/page/page_list.html");
});