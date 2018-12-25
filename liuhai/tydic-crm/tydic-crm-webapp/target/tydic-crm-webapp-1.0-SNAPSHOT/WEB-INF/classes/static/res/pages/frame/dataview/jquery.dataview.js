// JavaScript Document
var vm = 'data';
$.fn.dataview = function(opts) {
	var settings = {

	};
	$.extend(settings, opts);
	return this.each(function() {
		var data = settings.data;
		var image = data.image;// 图片代码
		var records = data.record;
		var tp;
		if (data.total == 0) {
			dataview.notFound(data.id);
		} else {
			tp = data.total % data.pagesize == 0
					? data.total / data.pagesize
					: Math.floor(data.total / data.pagesize) + 1;
			var hd = $('#' + data.id + '_hd');
			if (settings.detail){
				vm = 'data';
			}else{
				hd.find('span').append('(' + data.total + '条)');
//				var arr = $.cookie(user);
//				var key;
//				if(settings.url=='ryzh.jsp'){
//					key = 'ryzh_vm';
//				}else if(settings.url=='dhzh.jsp'){
//					key = 'dhzh_vm';
//				}else if(settings.url=='jdczh.jsp'){
//					key = 'jdczh_vm';
//				}
//				for(var i=0;i<arr.length;i++){
//					if(arr[i][key]){
//						vm = arr[i][key];
//						break;
//					}
//				}
			}
			hd.find('.dv_loading').hide();
			$('<div id="'
					+ data.id
					+ '_bd" class="dv_it_bd"><table class="dv_tb" cellpadding="0" cellspacing="0" align="center"></table></div>')
					.insertAfter($('#' + data.id + '_hd'));
			// dataview
			if (settings.detail)
				$('#' + data.id + '_bd .dv_tb:last')
						.append('<tr><td><table class="dv_data" cellpadding="0" cellspacing="0"><tr class="dv_tb_hd"></tr></table></td></tr>');
			else
				$('#' + data.id + '_bd .dv_tb:last')
						.append('<tr><td><table class="dv_data" cellpadding="0" cellspacing="0"><tr class="dv_tb_hd"><td class="dv_tb_hd_td" width="20"><div class="dv_col"></div></td><td class="dv_tb_hd_td" style="border-right:none;width:30px;">&nbsp;<input type="checkbox" /></td></tr></table></td></tr>');
			var header1 = data.header1;
			var last_hd = $('#' + data.id + '_bd .dv_tb_hd:last');
			for (i = 0; i < header1.length; i++) {// 生成简要表头
				if (header1[i].id != image) {
					last_hd.append('<td class="dv_tb_hd_td">&nbsp;'
							+ header1[i].name + '</td>');
				}
			}
			// photoview
			$('#' + data.id + '_bd .dv_tb:last')
					.append('<tr><td class="dv_pv"></td></tr>');
		}
		if (settings.detail) {
			dataview.loadRecord(data, image);
		} else {
			// pager
			var tp = data.total % data.pagesize == 0 ? data.total
					/ data.pagesize : Math.floor(data.total / data.pagesize)
					+ 1;
			if (data.total > data.pagesize) {
				$('#' + data.id + '_bd .dv_tb:last')
						.append('<tr><td><table id="'
								+ data.id
								+ '" class="dv_pager" cellpadding="0" cellpadding="0" align="right"><tr><td><div>共&nbsp;<span class="dv_total">1</span>&nbsp;页&nbsp;&nbsp;当前第&nbsp;<span class="dv_cur">1</span>&nbsp;页&nbsp;&nbsp;</div></td><td><table><tr><td class="dv_first" title="首页"></td><td class="dv_prev" title="上一页"></td><td class="dv_next" title="下一页"></td><td class="dv_last" title="末页"></td></tr></table></td><td>&nbsp;&nbsp;跳转到第&nbsp;<select class="dv_pdx"></select>&nbsp;&nbsp;页</td><tr></table></td></tr>');
				$('#' + data.id + '_bd .dv_total:last').text(tp);
				for (i = 1; i <= tp; i++) {
					$('#' + data.id + '_bd .dv_pdx:last').append('<option>' + i
							+ '</option>');
				}
			}
//			if(vm=='photo'&&!image){
//				$('#' + data.id + '_bd').hide();
//				$('#' + data.id + '_bd').prev().hide();
//				dataview.loadPage(data.id, 1, data.record, 'data', image);
//			}else{
//				dataview.loadPage(data.id, 1, data.record, vm, image);
//			}
			dataview.loadPage(data.id, 1, data.record, vm, image);	
		}
		$('#' + data.id + '_bd .dv_tb:last')
				.append('<tr class="dv_tb_bt"></tr>');
		// events
		if (!settings.detail) {
			$('#' + data.id + '_bd .dv_pdx').change(function() {// 页选择
						var tmp = $(this).parent().parent().parent().parent();
						var module = tmp.attr('id');
						var tp = tmp.find('span');
						var loading = $('#' + module + '_hd')
								.find('.dv_loading');
						loading.show();
						var c = $(this).find('option:selected').val();
						if ($.browser.msie)
							if ($.browser.version == '6.0'
									|| $.browser.version == '7.0')
								c = $(this).val();
						$.ajax({// 获取详细信息
							url : WEB_CONTEXT + '/web/query_do.appQuery',
							data : 'method=&appId=' + appId + '&modelId='
									+ module + '&exp=' + exp
									+ '&reqPage=' + c + '&pageSize=' + pageSize,
							type : 'POST',
							dataType : 'json',
							success : function(result) {
								dataview.loadPage(module, c, result.data, vm,
										image);
								$(tp[1]).text(c);
							}
						});
					});
			$('#' + data.id + '_bd .dv_first').click(function() {// 第一页
						var tmp = $(this).parent().parent().parent().parent();
						var sel = tmp.next().find('select');
						var tp = tmp.prev().find('span');
						var module = tmp.parent().parent().parent().attr('id');
						var c = sel.find('option:selected').val();
						if ($.browser.msie)
							if ($.browser.version == '6.0'
									|| $.browser.version == '7.0')
								c = sel.val();
						if (c != 1) {
							var loading = $('#' + module + '_hd')
									.find('.dv_loading');
							loading.show();
							$.ajax({// 获取详细信息
								url : WEB_CONTEXT + '/web/query_do.appQuery',
								data : 'method=&appId='
										+ appId
										+ '&modelId='
										+ module
										+ '&exp=' + exp
									+ '&reqPage=1&pageSize='
										+ pageSize,
								type : 'POST',
								dataType : 'json',
								success : function(result) {
									dataview.loadPage(data.id, 1, result.data,
											vm, image);
									$(tp[1]).text(1);
									sel[0].selectedIndex = 0;
								}
							});
						}

					});
			$('#' + data.id + '_bd .dv_prev').click(function() {// 前一页
						var tmp = $(this).parent().parent().parent().parent();
						var sel = tmp.next().find('select');
						var tp = tmp.prev().find('span');
						var module = tmp.parent().parent().parent().attr('id');
						var c = sel.find('option:selected').val();
						if ($.browser.msie)
							if ($.browser.version == '6.0'
									|| $.browser.version == '7.0')
								c = sel.val();
						if (c * 1 > 1) {
							var loading = $('#' + module + '_hd')
									.find('.dv_loading');
							loading.show();
							var loading = $('#' + module + '_hd')
									.find('.dv_loading');
							loading.show();
							$.ajax({// 获取详细信息
								url : WEB_CONTEXT + '/web/query_do.appQuery',
								data : 'method=&appId='
										+ appId
										+ '&modelId='
										+ module
										+ '&exp=' + exp
									+ '&reqPage='
										+ (c * 1 - 1) + '&pageSize=' + pageSize,
								type : 'POST',
								dataType : 'json',
								success : function(result) {
									dataview.loadPage(data.id, c * 1 - 1,
											result.data, vm, image);
									$(tp[1]).text(c * 1 - 1);
									sel[0].selectedIndex = c * 1 - 2;
								}
							});
						}
					});
			$('#' + data.id + '_bd .dv_next').click(function() {// 下一页
						var tmp = $(this).parent().parent().parent().parent();
						var sel = tmp.next().find('select');
						var tp = tmp.prev().find('span');
						var module = tmp.parent().parent().parent().attr('id');
						var c = sel.find('option:selected').val();
						if ($.browser.msie)
							if ($.browser.version == '6.0'
									|| $.browser.version == '7.0')
								c = sel.val();
						if (c * 1 < $(tp[0]).text() * 1) {
							var loading = $('#' + module + '_hd')
									.find('.dv_loading');
							loading.show();
							$.ajax({// 获取详细信息
								url : WEB_CONTEXT + '/web/query_do.appQuery',
								data : 'method=&appId='
										+ appId
										+ '&modelId='
										+ module
										+ '&exp=' + exp
									+ '&reqPage='
										+ (c * 1 + 1) + '&pageSize=' + pageSize,
								type : 'POST',
								dataType : 'json',
								success : function(result) {
									dataview.loadPage(data.id, c * 1 + 1,
											result.data, vm, image);
									$(tp[1]).text(c * 1 + 1);
									sel[0].selectedIndex = c * 1;
								}
							});
						}
					});
			$('#' + data.id + '_bd .dv_last').click(function() {// 末页
						var tmp = $(this).parent().parent().parent().parent();
						var sel = tmp.next().find('select');
						var tp = tmp.prev().find('span');
						var module = tmp.parent().parent().parent().attr('id');
						var c = sel.find('option:selected').val();
						if ($.browser.msie)
							if ($.browser.version == '6.0'
									|| $.browser.version == '7.0')
								c = sel.val();
						if (c != $(tp[0]).text()) {
							var loading = $('#' + module + '_hd')
									.find('.dv_loading');
							loading.show();
							$.ajax({// 获取详细信息
								url : WEB_CONTEXT + '/web/query_do.appQuery',
								data : 'method=&appId='
										+ appId
										+ '&modelId='
										+ module
										+ '&exp=' + exp
									+ '&reqPage='
										+ ($(tp[0]).text()) + '&pageSize='
										+ pageSize,
								type : 'POST',
								dataType : 'json',
								success : function(result) {
									dataview.loadPage(data.id, $(tp[0]).text(),
											result.data, vm, image);
									$(tp[1]).text($(tp[0]).text());
									sel[0].selectedIndex = $(tp[0]).text() * 1
											- 1;
								}
							});
						}
					});
			$('#' + data.id + '_hd').click(function() {// 模块显示隐藏
						if ($(this).attr('class') == 'dv_it_ni')
							return;
						var d = $(this).find('div');
						if (d.attr('class') == 'dv_exp') {
							d.attr('class', 'dv_col');
							$(this).next().hide();
						} else {
							d.attr('class', 'dv_exp');
							$(this).next().show();
						}
						dataview.resize();
					});
			$('#' + data.id + '_bd .dv_tb_hd_td div').click(function() {// 全显示隐藏
						var trs = $(this).parent().parent().parent()
								.find('.dv_tb_jy');
						for (var i = 0; i < trs.length; i++) {
							var tr = $(trs[i]);
							if (tr.next().attr('class') != 'dv_xx_row')
								dataview.loadDetail(
										tr.find('input').attr('id'), res, tr);
						}
						var dv_data = $(this).parent().parent().parent()
								.parent();
						var xx = dv_data.find('.dv_xx_row');
						if ($(this).attr('class') == 'dv_col') {
							dv_data.find('.dv_col').attr('class', 'dv_exp');
							dv_data.find('.dv_tb_jy').css('background-color',
									'#F4F4F4');
							xx.show();
						} else {
							dv_data.find('.dv_exp').attr('class', 'dv_col');
							dv_data.find('.dv_tb_jy').css('background-color',
									'transparent');
							xx.hide();
						}
						dataview.resize();
					});
			$('#' + data.id + '_bd .dv_tb_hd_td input').click(function() { // 全选事件
						var cs = $('#' + data.id + '_bd .dv_tb_jy_td input');
						if ($(this).attr('checked')) {
							for (k = 0; k < cs.length; k++) {
								if (!$(cs[k]).attr('checked')) {
									for (i = 0; i < selectRec.length; i++) {
										if ($(cs[k]).attr('id')
												.indexOf(selectRec[i].id) != -1) {
											if (!selectRec[i].record) {
												selectRec[i].record = new Array();
											}
											for (j = 0; j < data.record.length; j++) {
												if (data.record[j].id == $(cs[k])
														.attr('id')) {
													selectRec[i].record[selectRec[i].record.length] = data.record[j];
													break;
												}
											}
											break;
										}
									}
								}
							}
							cs.attr('checked', true);
						} else {
							for (k = 0; k < cs.length; k++) {
								if ($(cs[k]).attr('checked')) {
									for (i = 0; i < selectRec.length; i++) {
										if ($(cs[k]).attr('id')
												.indexOf(selectRec[i].id) != -1) {
											for (j = 0; j < selectRec[i].record.length; j++) {
												if (selectRec[i].record[j].id == $(cs[k])
														.attr('id')) {
													selectRec[i].record.splice(
															j, 1);
													break;
												}
											}
											break;
										}
									}
								}
							}
							cs.attr('checked', false);
						}
					});
		}
		if (settings.detail) {// 是否是详细页面
			$('.dv_tb_jy').css('background-color', '#F4F4F4');
			$('.dv_xx_row').show();
			// dataview.resize();
		}
	});
};
var dataview = {
	resize : function() {
		$(window.parent.document).find('.tab_body').css(
				'height',
				document.body.scrollHeight > 655 ? document.body.scrollHeight
						+ 2 : 655);
	},
	loadModel : function(jq, id, name) {
		jq
				.append('<table id="'
						+ id
						+ '_hd" class="dv_it"><tr><td width="10"><div class="dv_exp"></div></td><td><span style="float:left">'
						+ name
						+ '&nbsp;</span><div class="dv_loading"></div></td></tr></table>');
	},
	showAllModels : function() {
		$('.dv_it_ni').show();
	},
	hideEmptyModels : function() {
		$('.dv_it_ni').hide();
	},
	events : function(modelid, records, image) {
		$('#' + modelid + '_bd .dv_tb_jy_td div').click(function() {// 显示隐藏
					var tr = $(this).parent().parent();
					if (tr.next().attr('class') != 'dv_xx_row')
						dataview.loadDetail(tr.find('input').attr('id'), res,
								tr);
					var next = tr.next();
					if ($(this).attr('class') == 'dv_col') {
						$(this).attr('class', 'dv_exp');
						next.show();
					} else {
						$(this).attr('class', 'dv_col');
						next.hide();
					}
					dataview.resize();
				});
		$('#' + modelid + '_bd .dv_tb_jy').mouseover(function() {
					$(this).css('background-color', '#F4F4F4');
				});
		$('#' + modelid + '_bd .dv_tb_jy').mouseout(function() {
			if ($(this).next().attr('class') != 'dv_xx_row'
					|| $(this).next().css('display') == 'none')
				$(this).css('background-color', 'transparent');
		});
		$('#' + modelid + '_bd .dv_tb_jy_td input,#' + modelid
				+ '_bd .dv_ph_cbx input').click(function() { // 单选事件
					for (var i = 0; i < selectRec.length; i++) {// 寻找匹配模块
						if ($(this).attr('id').indexOf(selectRec[i].id) != -1) {
							if ($(this).attr('checked')) {// 加进缓存
								if (!selectRec[i].record) {
									selectRec[i].record = new Array();
								}
								for (j = 0; j < records.length; j++) {
									if (records[j].id == $(this).attr('id')) {
										selectRec[i].record[selectRec[i].record.length] = records[j];
										break;
									}
								}
							} else {// 从缓存中移除
								for (j = 0; j < selectRec[i].record.length; j++) {
									if (selectRec[i].record[j].id == $(this)
											.attr('id')) {
										selectRec[i].record.splice(j, 1);
										break;
									}
								}
							}
							break;
						}
					}
				});
	},
	loadPage : function(modelid, page, records, vm, image) {
		for (var i = 0; i < res.length; i++) {
			if (modelid == res[i].id) {
				res[i].record = records;
				break;
			}
		}
		$('#' + modelid + '_bd .dv_tb_jy,#' + modelid + '_bd .dv_xx_row')
				.remove();
		if (vm == 'photo') {
			$('#' + modelid + '_bd .dv_tb').css({
						border : 'none'
					});
			$('#' + modelid + '_bd .dv_tb_hd').hide();
		} else {
			$('#' + modelid + '_bd .dv_tb').css({
						border : '1px solid #99BBE8'
					});
			$('#' + modelid + '_bd .dv_tb_hd').show();
		}
		$('#' + modelid + '_bd .dv_ph_lst').remove();
		if (page > 0)
			for (var i = 0; i < records.length; i++) {// 给记录加编号
				records[i].id = modelid + '_' + page + '_' + i;
			}
		var cache;
		for (i = 0; i < selectRec.length; i++) {// 取出本模块已选中的记录
			if (modelid == selectRec[i].id) {
				cache = selectRec[i].record;
				break;
			}
		}
		if (vm == 'data') {// 普通模式
			var header1, header2, header3;
			for (var i = 0; i < selectRec.length; i++) {
				if (selectRec[i].id == modelid) {
					header1 = selectRec[i].header1;
					header2 = selectRec[i].header2;
					header3 = selectRec[i].header3;
					break;
				}
			}

			var tr = $('#' + modelid + '_bd .dv_tb_hd');
			for (i = 0; i < records.length; i++) {
				// 添加简要信息行
				$('<tr class="dv_tb_jy"><td class="dv_tb_jy_td"><div class="dv_col"></div></td><td class="dv_tb_jy_td">&nbsp;<input id="'
						+ records[i].id + '" type="checkbox" /></td></tr>')
						.insertAfter(tr);
				tr = tr.next();
				for (j = 0; j < header1.length; j++) {
					if (header1[j].id != image) {
						var last_rec = $('#' + modelid + '_bd .dv_tb_jy:last');
						last_rec.append('<td class="dv_tb_jy_td">&nbsp;'
								+ records[i][header1[j].id] + '</td>');
					}
				}
				if (cache)
					for (j = 0; j < cache.length; j++) {
						if (cache[j].id == records[i].id) {
							$('#' + records[i].id).attr('checked', true);
						}
					}
			}
		} else if (vm == 'photo') {// 照片模式
			$('#' + modelid + '_bd .dv_pv').photoview({
						data : records
					});
			$('#' + modelid + '_bd .dv_ph_lst').show();
			if (cache) {
				for (i = 0; i < records.length; i++) {
					for (j = 0; j < cache.length; j++) {
						if (cache[j].id == records[i].id) {
							$('#' + records[i].id).attr('checked', true);
						}
					}
				}
			}
		}
		$('.dv_xx_div').remove();
		this.events(modelid, records, image);// 重新绑定事件
		var loading = $('#' + modelid + '_hd').find('.dv_loading');
		loading.hide();
		dataview.resize();
	},
	loadRecord : function(data, image) {
		var header1 = data.header1;
		var header2 = data.header2;
		var header3 = data.header3;
		var tr = $('#' + data.id + '_bd .dv_tb_hd');
		var colspan = tr.find('td').length;
		for (i = 0; i < data.record.length; i++) {
			// 添加简要信息行
			$('<tr class="dv_tb_jy"></tr>').insertAfter(tr);
			tr = tr.next();
			// 添加详细信息行
			$('<tr class="dv_xx_row"><td colspan="'
					+ colspan
					+ '" class="dv_xx"><table class="dv_tb_xx" align="center"></table></td></tr>')
					.insertAfter(tr);
			tr = tr.next();
			for (j = 0; j < header1.length; j++) {
				if (header1[j].id != image) {
					var last_rec = $('#' + data.id + '_bd .dv_tb_jy:last');
					last_rec.append('<td class="dv_tb_jy_td">&nbsp;'
							+ data.record[i][header1[j].id] + '</td>');
				}
			}
			for (j = 0; j < header2.length; j++) {
				var last_xxtb = $('#' + data.id + '_bd .dv_tb_xx:last');
				last_xxtb.append('<tr></tr>');
				var curtr = last_xxtb.find('tr:last');
				var ln2 = header2[j].id ? header2[j].name + '：' : '&nbsp;';
				var lt2;
				if (header2[j].id) {
					if (data.record[i][header2[j].id] == '') {
						lt2 = '---';
					} else {
						lt2 = data.record[i][header2[j].id];
					}
				} else {
					lt2 = '&nbsp;';
				}
				var ln3 = header3[j].id ? header3[j].name + '：' : '&nbsp;';
				var lt3;
				if (header3[j].id) {
					if (data.record[i][header3[j].id] == '') {
						lt3 = '---';
					} else {
						lt3 = data.record[i][header3[j].id];
					}
				} else {
					lt3 = '&nbsp;';
				}
				curtr.append('<td width="15%" class="dv_xx_hd">' + ln2
						+ '</td>');
				curtr.append('<td width="25%">' + lt2 + '</td>');
				curtr.append('<td width="15%" class="dv_xx_hd">' + ln3
						+ '</td>');
				curtr.append('<td width="25%">' + lt3 + '</td>');
				if (j == 0) {
					var img = '';
					var dimg = '';
					if (image) {
						img = '<img src="../../tempImg/'
								+ data.record[i][image] + '" />';
						dimg = '<img src="../../images/toux.jpg" />';
					}
					if (data.record[i][image] && data.record[i][image] != '')
						curtr.append('<td width="20%" rowspan="'
								+ header2.length
								+ '" align="right" valign="middle">' + img
								+ '</td>');
					else
						curtr.append('<td width="20%" rowspan="'
								+ header2.length
								+ '" align="right" valign="middle">' + dimg
								+ '</td>');
				}
			}
			$('.dv_tb_xx').css('background', 'url("' + bgimg + '")');
		}
	},
	loadDetail : function(rid, data, jq, vm) {
		// 添加详细信息行
		var modelid, record, header1, header2, header3, image;
		for (var i = 0; i < data.length; i++) {
			if (rid.indexOf(data[i].id) != -1) {
				modelid = data[i].id;
				header1 = data[i].header1;
				header2 = data[i].header2;
				header3 = data[i].header3;
				image = data[i].image;
				var rs = data[i].record;
				for (var j = 0; j < rs.length; j++) {
					if (rid == rs[j].id) {
						record = rs[j];
						break;
					}
				}
				break;
			}
		}
		var xxtb;
		if (vm == 'photo') {
			$('<div id="_'
					+ rid
					+ '" class="dv_xx_div"><table class="dv_tb_xx" align="center"></table></div>')
					.insertAfter(jq);
		} else {
			var colspan = jq.find('td').length;
			$('<tr class="dv_xx_row"><td colspan="'
					+ colspan
					+ '" class="dv_xx"><table class="dv_tb_xx" align="center"></table></td></tr>')
					.insertAfter(jq);
		}
		$('.dv_tb_xx').css('background', 'url("' + bgimg + '")');
		xxtb = jq.next().find('.dv_tb_xx');
		for (j = 0; j < header2.length; j++) {
			xxtb.append('<tr></tr>');
			var curtr = xxtb.find('tr:last');
			var ln2 = header2[j].id ? header2[j].name + '：' : '&nbsp;';
			var lt2;
			if (header2[j].id) {
				if (record[header2[j].id] == '') {
					lt2 = '---';
				} else {
					lt2 = record[header2[j].id];
				}
			} else {
				lt2 = '&nbsp;';
			}
			var ln3 = header3[j].id ? header3[j].name + '：' : '&nbsp;';
			var lt3;
			if (header3[j].id) {
				if (record[header3[j].id] == '') {
					lt3 = '---';
				} else {
					lt3 = record[header3[j].id];
				}
			} else {
				lt3 = '&nbsp;';
			}
			if (header2[j].id == image) {
				ln2 = '&nbsp;';
				lt2 = '&nbsp;';
			}
			if (header3[j].id == image) {
				ln3 = '&nbsp;';
				lt3 = '&nbsp;';
			}
			curtr.append('<td width="15%" class="dv_xx_hd">' + ln2 + '</td>');
			curtr.append('<td width="25%">' + lt2 + '</td>');
			curtr.append('<td width="15%" class="dv_xx_hd">' + ln3 + '</td>');
			curtr.append('<td width="25%">' + lt3 + '</td>');
			if (j == 0) {
				var img = '';
				var dimg = '';
				if (image) {
					img = '<img src="../../tempImg/' + record[image] + '" />';
					dimg = '<img src="../../images/toux.jpg" />';
				}
				if (record[image] && record[image] != '') {
					curtr.append('<td width="20%" rowspan="' + header2.length
							+ '" align="right" valign="middle">' + img
							+ '</td>');
				} else {
					curtr.append('<td width="20%" rowspan="' + header2.length
							+ '" align="right" valign="middle">' + dimg
							+ '</td>');
				}
			}
		}
	},
	notFound : function(id) {
		var hd = $('#' + id + '_hd');
		hd.attr('class', 'dv_it_ni');
		hd.find('span').append('(对不起，没有你要的查询结果！)');
		hd.find('.dv_loading').hide();
	}
};