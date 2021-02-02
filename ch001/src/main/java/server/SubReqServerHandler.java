package server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protobuf.SubscribeReqProto;
import protobuf.SubscribeRespProto;

/**
 * @author jayying
 * @since 2021/2/2
 */
@Sharable
public class SubReqServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq) msg;
        if("Jayying".equalsIgnoreCase(req.getUserName())) {
            System.out.println("Service accept client req : [" + req.toString() + "]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }

    }
    private SubscribeRespProto.SubscribeResp resp(int subReqID) {
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqID(subReqID);
        builder.setRespCode(10);
        builder.setDesc("Netty Book order Succeed.");
        return builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
