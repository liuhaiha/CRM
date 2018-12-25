// 修改权限信息
function modPermission(id) {
	findPermissionById(id);
	//document.getElementById('per_code').disabled = false;
	document.getElementById('per_title').disabled = false;
	layer.open({
		  title: '修改权限信息：'
		  ,type: 1
		  ,content: $('#modPermission')
		  ,offset:160
		  ,shade:false
		  ,shadeClose:false
		  ,btn: ['修改', '取消']
		  ,yes: function(index, layero){
			//var code=document.getElementById('per_code').value;
			var title = document.getElementById('per_title').value;
			var icon =  document.getElementById('per_iconType').value;
			var perUrl = document.getElementById('per_url').value;
			
			if (title == "") {
				alert('权限名称不能为空');
				return false;
			}
			if (icon == "") {
				alert('请选择菜单图标');
				return false;
			}
			
			$.ajax({
				type     : "POST",
				url      : "../../saleTrack/permission/update",
				dataType : "json",
				data     : {"id":id, "title":title, "icon":"&" + icon, "url":perUrl},
				success  : function(result) {
					if (result == 1) {
						layer.msg('修改成功');
						showPermissionList(code);
						refleshTree();
					}
				},
				error    : function(err) {
					alert('修改失败，请重试，' + err);
				}
			});
			
			layer.close(index); //如果设定了yes回调，需进行手工关闭
		  }
		  ,cancel: function(index, layero){ 
			layer.close(index); //如果设定了yes回调，需进行手工关闭
	  }});
}

// 查询单条权限信息
function findPermissionById(id) {
	$.ajax({
		type     : "GET",
		url      : "../../saleTrack/findPermissionById/" + id,
		dataType : "json",
		success  : function(result) {
			$('#per_code').val(result.code);
			$('#per_title').val(result.title);
			$('#per_iconType').val(result.icon.substr(1));
			$('#per_url').val(result.url);
			$('#per_creator').val(result.creator);
			$('#per_createtime').val(result.createtime);
		},
		error    : function(err) {
			alert('查询失败，请重试，' + err);
		}
	});
}

// 删除权限信息
function delPermission(id)
{
	layer.confirm('确认删除?',{
		title:'提示:将删除该条权限数据'
		,offset:160
		,btn:['确认', '取消']
		,yes:function(index,layerno)
		{
			$.ajax({
				type     : "GET",
				url      : "../../saleTrack/delPermission/" + id,
				dataType : "json",
				success  : function(result) {
					if (result == 1) {
						layer.msg('删除成功');
						showPermissionList(code);
						refleshTree();
					}
				},
				error    : function(err) {
					alert('删除失败，' + err);
				}
			});
			layer.close(index);
		}
	});
}

// 单条权限数据详细信息
function detailPermission(id) {
	//document.getElementById('per_code').disabled = true;
	document.getElementById('per_title').disabled = true;
	document.getElementById('per_iconType').disabled = true;
	document.getElementById('per_url').disabled = true;
	findPermissionById(id);
	layer.open({
		title: '该条权限详细信息为：'
		,type: 1
		,content: $('#modPermission')
		,offset:160
		,shade:false
		,shadeClose:false
		,btn: ['确认']
		,yes: function(index, layero){
			layer.close(index);
			//document.getElementById('per_code').disabled = false;
			document.getElementById('per_title').disabled = false;
			document.getElementById('per_iconType').disabled = false;
			document.getElementById('per_url').disabled = false;
		}
	});
}