$(document).ready(function(){
	var menu = {
		init : function() {
			tableList.init();
			this.getmenu();
			this.bindEvent();
		},
		bindEvent : function() {
			$("#addmenu").on("click", menu.addmenu);
			$("#deletemenu").on("click", menu.deletemenu);
		},
		getmenu : function() {
			$("#org_tree").empty();
			$.ajax({
				async : false,
				type : 'GET',
				url : '/crm/saleFile/listMenu',
				success : function(json) {
					layui.use('tree', function() {
						layui.tree({
							elem : '#org_tree' // 传入元素选择器
							,
							nodes : json.data,
							click : function(node) {
								$('#cid').val(node.cid);
								tableList.params.fname='';
								tableList.params.creator='';
								tableList.params.ftype='';
								tableList.params.pageNo='';
								tableList.params.fclass=node.code;
								tableList.queryFiles(null);
								$('#dataTitle cite').text("售前资料管理："+node.name);
								for(var i=0;i<this.nodes.length;i++){
									if(this.nodes[i].cid==node.cid){
										$('#flag').val('1');
										return ;
									}
								}
								$('#flag').val('0');
							}
						});
						$('#org_tree a').eq(0).click();
					});
				},
				error : function(xhr, error, exception) {
					console.log(error);
				}
			});
		},
		addmenu : function() {
			layui.use('layer', function() {
				layer = layui.layer;
				if($('#flag').val()!="1"){
					layer.msg('不能添加多级菜单');
					return ;
				}
				$('#menuName').val('');
				layer.open({
					title : '添加菜单',
					type : 1,
					content : $('#addmenulist'),
					shade : false,
					shadeClose : false,
					btn : [ '新增', '取消' ],
					btnAlign:'c',
					yes : function(index, layero) {
						if($('#menuName').val()==''){
							layer.msg('菜单名不能为空');
							return;
						}
						// 按钮【按钮一】的回调
						$.ajax({
							async : false,
							type : 'POST',
							data : {
								'name' : $('#menuName').val()
							},
							url : '/crm/saleFile/addMenu/' + $("#cid").val(),
							success : function() {
								layer.msg("添加菜单成功");
								menu.getmenu();
							},
							error : function(xhr, e, exception) {
								layer.msg("添加菜单失败");
							}
						});
						layer.close(index); // 如果设定了yes回调，需进行手工关闭

					},
					cancel : function(index, layero) {
						// 右上角关闭回调

						// return false 开启该代码可禁止点击该按钮关闭
						layer.close(index); // 如果设定了yes回调，需进行手工关闭
					}
				});
			});
		},
		deletemenu : function() {
			layui.use('layer', function() {
				layer = layui.layer;
				layer.confirm('确认删除?', {
					title : '删除',
					btnAlign:'c',
					btn : [ '确认', '取消' ],
					btn1 : function(index, layero) {
						$
								.ajax({
									async : true,
									type : 'DELETE',
									url : '/crm/saleFile/deleteMenu/'
											+ $('#cid').val(),
									success : function() {
										layer.msg('删除成功');
										menu.getmenu();
									},
									error : function() {
										layer.msg('删除失败');
									}
								});
						layer.close(index);
					}
				})
			});
		},
	}
	menu.init();
});
