package buaa.act.baseService;

import net.sf.json.JSONObject;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * Created by luckcul on 17-6-11.
 * 现在已经弃用，而且不建议使用这种方法。
 */
public class GetUserInfo {
    JSONObject json;
    public void getUserInfo(int userId, int belongTo) throws UnknownHostException {
        TransportClient client = ESConnection.getConnection();
        MatchQueryBuilder qb1 = matchQuery("userID", userId);
        MatchQueryBuilder qb2 = matchQuery("profile.belongTo", belongTo);
        QueryBuilder qb;
        qb = boolQuery()
                .must(qb1).must(qb2);
        String platform;
        if(belongTo == 0) platform = "github";
        else if(belongTo == 1) platform = "topcoder";
        else if(belongTo == 2) platform = "so";
        else platform = "csdn";
        SearchResponse response = client.prepareSearch(platform)
//                .setTypes("UserInfo")
//		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(qb)                                                       // Query
//		        .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
                .setFrom(0).setSize(10)
//		        .setExplain(true)
                .setFetchSource(null, new String[]{"skills.document"})
                .get();
//        System.out.println(response.getHits().toString());
//        System.out.println(response.getHits().getAt(0).getSourceAsString());
//        this.json = JSONObject.fromObject(response.getHits().getAt(0).getSource());;
        this.json = JSONObject.fromObject(response.getHits().getAt(0).getSourceAsString());

//        for (SearchHit hit : response.getHits().getHits()) {
//            System.out.println(hit.getSourceAsString());
//        }
//        client.close();
    }

    public JSONObject getJson(){
//        System.out.println(json.toString());
        return this.json;
    }
//    public static void main(String args[]) throws UnknownHostException {
//        GetUserInfo x = new GetUserInfo();
//        x.getUserInfo(36901, 2);
////        System.out.println(x.json.toString());
//    }
}
