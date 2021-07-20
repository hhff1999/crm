<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>
    <base href="<%=basePath%>">
    <title>演示bs_typeahead插件</title>
    <script type="text/javascript">
        $(function () {
            //当容器加载完成，对容器调用工具函数
            var name2id={};
            $("#customerName").typeahead({
                // source:['阿里巴巴','京东商城','腾讯','动力节点','字节跳动']
                source:function (query,process) {
                    $.ajax({
                        url:'workbench/transaction/typeahead.do',
                        data:{
                            customerName:query
                        },
                        type:'post',
                        dataType:'json',
                        success:function (data) {//data就是json的字符串数组
                            // alert(data.length);
                            var customerNameArr=[];
                            //遍历data复杂类型数组
                            $.each(data,function (index,obj) {
                                //生成简单类型数组
                                customerNameArr.push(obj.name);
                                //把obj的name和id赋值给name2id，把name作为name2id属性名，id作为name2id的属性值
                                name2id[obj.name]=obj.id;
                            });
                            process(customerNameArr);
                        }
                    });
                },
                afterSelect:function (item) {//用户选中一项之后，自动触发本函数； item：选中项，补全之后的名字
                    $("#customerName").val(name2id[item]);
                }
            });
        });
    </script>
</head>
<body>
<input type="text" id="customerName">
</body>
</html>
