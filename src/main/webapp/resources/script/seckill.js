// 存放主要交互逻辑js代码
// javascript 模块化

var seckill = {
    // 封装秒杀相关ajax的url
    URL : {
        now : function(){
            return "/seckill/time/now" ;
        }
    },
    handleSeckill : function(seckillId, node){
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');

    },
    // 验证手机号
    validatePhone : function (phone) {
        if(phone && phone.length == 11 && !isNaN(phone)){
            return true;
        }else{
            return false;
        }
    },
    countdown : function(seckillId,nowTime,startTime,endTime){

        var seckillBox = $('#seckill-box');

        // 时间判断
        if(nowTime > endTime){
            // 秒杀结束
            seckillBox.html('秒杀结束!');
        }else if(nowTime < startTime){
            // 秒杀未开始,计时
            var killTime = new Date(startTime + 1000); // 加上1秒防止偏移
            seckillBox.countdown(killTime,function(event){
                // 时间格式
                var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒')
                seckillBox.html(format)
            }).on('finish.countdown',function(){
                // 时间完成后回调事件，获取秒杀地址，控制实现逻辑，执行秒杀
                seckill.handleSeckill(seckillId,seckillBox);
            });
        }else{
            // 秒杀开始
            seckill.handleSeckill(seckillId,seckillBox);
        }
    },
    // 详情页秒杀逻辑
    detail: {

        // 详情页初始化
        init : function(params){
            // 手机验证和登录,计时交互

            // 在cookie中查找手机号
            var killPhone = $.cookie('killPhone');

            console.log("killPhone: " + killPhone + "  startTime: " + startTime + " endTime: " + endTime + "  seckillId: " + seckillId);

            // 验证手机号
            if(!seckill.validatePhone(killPhone)) {
                // 绑定phone
                // 控制输出
                var killPhoneModal = $('#killPhoneModal');

                // 显示弹出层
                killPhoneModal.modal({
                    // 显示弹出层
                    show: true,
                    backdrop: 'static' , //禁止位置关闭
                    keyboard: false // 关闭键盘事件
                });
                $('#killPhoneBtn').click(function(){
                    var inputPhone = $('#killPhoneKey').val();
                    if(seckill.validatePhone(inputPhone)){
                        // 电话写入cookie
                        $.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
                        //刷新页面
                        window.location.reload();
                    }else{
                        $('#killPhoneMessage').hide().html('<lable class="label label-danger">手机号错误！</lable>').show(300);
                    }
                });
            }

            // 计时交互
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.get(seckill.URL.now(),{},function(result){
                if(result && result['success']){
                    var nowTime = result['data'];
                    // 时间判断
                    seckill.countdown(seckillId,nowTime,startTime,endTime);
                }else{
                    console.log('result:' + result)
                }
            });
        }


    }
}