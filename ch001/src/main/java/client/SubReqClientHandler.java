package client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protobuf.SubscribeReqProto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jayying
 * @since 2021/2/2
 */
public class SubReqClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        for(int i = 1; i <= 10; i++) {
            ctx.write(subReq(i));
        }
        ctx.flush();
    }

    public SubscribeReqProto.SubscribeReq subReq(int i) {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(i);
        builder.setUserName("Jayying");
        builder.setProductName("Netty Book of Protobuf");
        List<String> address = new ArrayList<>();
        address.add("Foshan");
        address.add("Shantou");
        address.add("Shenzhen");
        builder.addAllAddress(address);

        return builder.build();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("Receive server response [" + msg.toString() + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
