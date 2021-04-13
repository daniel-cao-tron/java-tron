package org.tron.core.services.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tron.api.GrpcAPI;
import org.tron.core.Wallet;

@Component
public class GetCrossChainVoteDetailListServlet extends RateLimiterServlet {

  @Autowired
  private Wallet wallet;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    try {
      boolean visible = Util.getVisible(request);
      long offset = Long.parseLong(request.getParameter("offset"));
      long limit = Long.parseLong(request.getParameter("limit"));
      String chainId = request.getParameter("chainId");
      int round = Integer.parseInt(request.getParameter("round"));
      GrpcAPI.CrossChainVoteDetailList reply =
              wallet.getCrossChainVoteDetailList(offset, limit, chainId, round);
      if (reply != null) {
        response.getWriter().println(JsonFormat.printToString(reply, visible));
      } else {
        response.getWriter().println("{}");
      }
    } catch (Exception e) {
      Util.processError(e, response);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    try {
      PostParams params = PostParams.getPostParams(request);
      String input = params.getParams();
      boolean visible = params.isVisible();
      GrpcAPI.CrossChainVotePaginated.Builder build = GrpcAPI.CrossChainVotePaginated.newBuilder();
      JsonFormat.merge(input, build, visible);
      GrpcAPI.CrossChainVoteDetailList reply = wallet.getCrossChainVoteDetailList(
              build.getOffset(), build.getLimit(), build.getChainId().toString(),build.getRound());
      if (reply != null) {
        response.getWriter().println(JsonFormat.printToString(reply, visible));
      } else {
        response.getWriter().println("{}");
      }
    } catch (Exception e) {
      Util.processError(e, response);
    }
  }
}
