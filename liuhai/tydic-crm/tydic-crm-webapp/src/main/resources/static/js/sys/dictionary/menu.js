$(document).ready(function(){
	var menu = {
		init : function() {
			tableList.init(menu);
			this.bindEvent();
			this.getmenu(function(){
				$('#org_tree a').eq(0).click();
			});
		},
		bindEvent : function() {
			
		},
		getmenu : function(callback) {
			$("#org_tree").empty();
			$.ajax({
				async : false,
				type : 'GET',
				url : '/crm/sys/getCodeMenu',
				success : function(json) {
					layui.use('tree', function() {
						layui.tree({
							elem : '#org_tree' // 传入元素选择器
							,
							nodes : json.data,
							click : function(node) {
								$('#cid').val(node.cid);
								$('#clicked_menu').val(JSON.stringify(node));
								tableList.queryDic(1);
								$('#dataTitle cite').text("字典管理："+node.name);
							}
						});
						if(callback!=null)
							callback();
					});
				},
				error : function(xhr, error, exception) {
					console.log(error);
				}
			});
		}
	}
	menu.init();
});
