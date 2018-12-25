(function(){
	var layer;
	var workdesk={
		init:function(){
	        layui.config({
	                base: '../../plugins/layui/modules/'
	            });
	        layui.use('layer', function() {
	                layer = layui.layer;
	                
	        });
			this.getBirthday();
			this.getProjectBeyondMonth();
			this.getUnapproveList();
			this.getBacklogList();
			this.bindEvent();
		},
		bindEvent:function(){
			$("#birhdayList").on('click','a',this.getCustomerMesg);
			$("#backlogList").on('click','a',this.viewDetails);
			$("#unapproved").on('click','a',this.toApproval);
		},
		getCustomerMesg:function(){//打开客户信息的事件
			var suffix=$(this).attr("data-id");
			layer.open({
			  title: '客户信息'
			  ,id:999
			  ,type: 1
			  ,area:'21%'
			  ,anim:5
			  ,content: $('#customer'+suffix).html()
			  ,shade:false
			  ,shadeClose:false
			});
		},
		getBirthday:function(){//获取一周内客户的生日
			$.ajax({
				async:true,
				type:'GET',
				url:'/crm/workDesk/getBirthdayList',
				success:function(json){
					var data=json.data;
					if(data==null || data==""){
						$('#birhdayList').empty();
						$('#customer_mesg').empty();
						$("#bbb").css("display", "none");
					}else{
						var html='';
						var html1='';
						for(var i=0,len=data.length;i<len;i++){
							html+='<li style="margin:10px;">';
							html+=i+1+'、'+data[i].next_birthday+'：'+data[i].super_dept_zh+' '+data[i].dept_zh+' '+' <a style="display: inline-block;color:a00;cursor:pointer;" data-id="'+data[i].lid+'">'+data[i].name+'</a> 生日</li>';
							
							html1+='<div id="customer'+data[i].lid+'"><div class="modal-body" style="padding: 5px;"><table class="site-table table-hover"><thead>';
							
							html1+='<tr><td style="font-size: 14px; text-align: right;">姓名：</td>';
							html1+='<td style="font-size: 14px;"><input type="text"style="width: 98%; height: 30px;" value="'+data[i].name+'" readonly/></td></tr>';
							html1+='<tr><td style="font-size: 14px; text-align: right;">性别：</td>';
							html1+='<td style="font-size: 14px;"><input type="text"style="width: 98%; height: 30px;" value="'+data[i].sex_zh+'" readonly/></td></tr>';
							html1+='<tr><td style="font-size: 14px; text-align: right;">办公电话：</td>';
							html1+='<td style="font-size: 14px;"><input type="text"style="width: 98%; height: 30px;" value="'+data[i].officephone+'" readonly/></td></tr>';
							html1+='<tr><td style="font-size: 14px; text-align: right;">联系电话：</td>';
							html1+='<td style="font-size: 14px;"><input type="text"style="width: 98%; height: 30px;" value="'+data[i].telephone+'" readonly/></td></tr>';
							html1+='<tr><td style="font-size: 14px; text-align: right;">e-mail：</td>';
							html1+='<td style="font-size: 14px;"><input type="text"style="width: 98%; height: 30px;" value="'+data[i].email+'" readonly/></td></tr>';
							html1+='</thead><tbody></tbody></table></div></div>';
						}
						$('#birhdayList').html(html);
						$('#customer_mesg').html(html1);
					}
				},
				error:function(){
					$('#birhdayList').empty();
					$('#customer_mesg').empty();
					layer.msg('加载错误');
				}
			});
		},
		getUnapproveList:function(){//获取未审批事件
			$.ajax({
				async:true,
				type:'GET',
				url:'/crm/workDesk/getUnapproveList',
				success:function(json){
					var data=json.data;
					$('#unapproved').empty();
					if(data==null || data==""){
						$("#aaa").css("display", "none");
						return ;
					}
					var html3='';
					for(var i=0,len=data.length;i<len;i++){
						//未审批
						html3+='<li style="margin:10px;">';
						html3+=i+1+'、'+data[i].ext_time.slice(0,10)+'：'+data[i].cname/*+' '+data[i].ext_theme*/;
						if(data[i].ext_owner!=undefined && data[i].ext_owner!=null && data[i].ext_owner!=""){
							html3+='<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;参与人：'+data[i].ext_owner.join("、");
						}
						html3+='&nbsp;&nbsp;&nbsp;&nbsp;<a style="display: inline-block;color:a00;cursor:pointer;" data-id="'+data[i].aid+'" href="javascript:void(0);">查看详情</a></li>';
					}
					$('#unapproved').html(html3);
				},
				error:function(xhr,e,error){
					$('#unapproved').empty();
					layer.msg('加载错误');
				}
			});
		},
		getBacklogList:function(){//获取小组于个人待办事件
			$.ajax({
				async:true,
				type:'GET',
				url:'/crm/workDesk/getBacklogList',
				success:function(json){
					var data=json.data;
					$('#backlogList').empty();
					$('#backlogGroupList').empty();
					if(data==null || data==""){
						$("#ccc").css("display", "none");
						$("#ddd").css("display", "none");
						return ;
					}
					var html='';
					var html1='';
					var html3='';
					for(var i=0,j=k=1,len=data.length;i<len;i++){
						if(data[i].appr_status=="1"){
							//个人
							html+='<li style="margin:10px;">';
							html1+='<li style="margin:10px;">';
							html+=k+'、'+data[i].ext_time.slice(0,10)+'：'+data[i].cname+/*' '+data[i].ext_theme+' '+*/' &nbsp;&nbsp;&nbsp;&nbsp;<a style="display: inline-block;color:a00;cursor:pointer;" data-id="'+data[i].aid+'">查看详情</a></li>';
							k++;
							//小组
							if(data[i].atype=='02'){
								html1+=j+'、'+data[i].ext_time.slice(0,10)+'：'+data[i].cname+/*' '+data[i].ext_theme+*/'<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;参与人：'+data[i].ext_owner.join("、")+'&nbsp;&nbsp;&nbsp;&nbsp;<a style="display: inline-block;color:a00;cursor:pointer;" data-id="'+data[i].aid+'" href="/crm/sale/viewDetail?aid='+data[i].aid+'&t=' + Math.random() +'">查看详情</a></li>';
								j++;
							}
						}
					}
					$('#backlogList').html(html);
					$('#backlogGroupList').html(html1);
				},
				error:function(xhr,e,error){
					$('#backlogList').empty();
					$('#backlogGroupList').empty();
					$('#unapproved').empty();
					layer.msg('加载错误');
				}
			});
		},
		getProjectBeyondMonth:function(){//获取超过一个月未跟进项目
			$.ajax({
				async:true,
				type:'GET',
				url:'/crm/workDesk/getProjectBeyondMonth',
				success:function(json){
					var data=json.data;
					$('#projectBeyondMonth').empty();
					if(data==null || data==""){
						$("#eee").css("display", "none");
						return ;
					}
					var html='';
					for(var i=0,len=data.length;i<len;i++){
						html+='<li style="margin:10px;">';
						if(data[i].sub_mtime==undefined || data[i].sub_mtime==null || data[i].sub_mtime=='null'){
							html+=i+1+'、';
							if(data[i].full_name!=undefined && data[i].full_name!=null && data[i].full_name!=""){
								html+=data[i].full_name;
							}else{
								html+=data[i].short_name;
							}
							html+=' 上次跟进日期为：'+data[i].createtime.slice(0,10)+'，已有<span style="color:red">'+data[i].ctime+'</span>日未有跟进</li>';
						}else{
							html+=i+1+'、';
							if(data[i].full_name!=undefined && data[i].full_name!=null && data[i].full_name!=""){
								html+=data[i].full_name;
							}else{
								html+=data[i].short_name;
							}
							html+=data[i].full_name+' 上次跟进日期为：'+data[i].mtime.slice(0,10)+'，已有<span style="color:red">'+data[i].sub_mtime+'</span>日未有跟进</li>';
						}
					}
					$('#projectBeyondMonth').html(html);
				},
				error:function(xhr,e,error){
					$('#projectBeyondMonth').empty();
					layer.msg('加载错误');
				}
			});
		},
		viewDetails:function(){
			var aid = $(this).attr("data-id");
			location.href= "/crm/sale/viewDetail?aid=" + aid + "&t=" + Math.random();
		},
		toApproval:function(){
			var aid = $(this).attr("data-id");
			location.href= "/crm/sale/toApproval?aid=" + aid + "&t=" + Math.random();
		}
	};
	workdesk.init();
})()