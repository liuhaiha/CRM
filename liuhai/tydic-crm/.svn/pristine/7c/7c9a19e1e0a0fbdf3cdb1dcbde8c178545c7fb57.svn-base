// 新增客户信息
function addCustomer() {
	// var result = true;
	var fields = ['name', 'region', 'province', 'resource', 'unit_type'];
	var filedNames = ['客户名称', '所属区域', '所属省份', '客户来源', '单位类别'];
	var len = fields.length;
	for (var i = 0; i< len; i++)
	{
		if ($("#cus_" + fields[i]).val() == "")
		{
			alert(filedNames[i] + "不能为空");
			return false;
		}
	}
	
	var cname = document.getElementById('cus_name').value;
	var addr = document.getElementById('cus_addr').value;
	var region = document.getElementById('cus_region').value;
	var province = document.getElementById('cus_province').value;
	var resource = document.getElementById('cus_resource').value;
	var unitType = document.getElementById('cus_unit_type').value;
	$.ajax({
		type     : "POST",
		url      : "../../base/customer/create",
		dataType : "json",
		data     : {"cname":cname, "addr":addr, "region":region, "province":province, "resource":resource, "unitType":unitType},
		success  : function(data) {
			layer.msg('新增成功');
			for (var i = 0; i< len; i++)
			{
				$("#cus_" + fields[i]).val("");
			}
		}, 
		error    : function() {
			alert('新增失败，请重试');
		}
	});
}

// 修改客户信息
function modCustomer() {
	var fields = ['name','region','province','resource','unit_type'];
	var filedNames = ['客户名称','所属区域','所属省份','客户来源','单位类别'];
	var len = fields.length;
	var cname = document.getElementById('cus_name').value;
	var addr = document.getElementById('cus_addr').value;
	var region = document.getElementById('cus_region').value;
	var province = document.getElementById('cus_province').value;
	var resource = document.getElementById('cus_resource').value;
	var unitType = document.getElementById('cus_unit_type').value;
	
	for (var i = 0; i< len; i++)
	{
		if ($("#cus_" + fields[i]).val() == "")
		{
			alert(filedNames[i] + "不能为空");
			return false;
		}
	}
	
	$.ajax({
		type     : "POST",
		url      : "../../base/customer/update",
		dataType : "json",
		data     : {"cid":treeId, "cname":cname, "addr":addr, "region":region, "province":province, "resource":resource, "unitType":unitType},
		success  : function(data) {
			if (data) {
				layer.msg('修改成功');
				refleshTree();
			} else {
				alert('修改失败，请重试');
			}
		}, 
		error    : function() {
			alert('修改失败，请重试');
		}
	});
}