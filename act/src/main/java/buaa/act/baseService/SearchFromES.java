package buaa.act.baseService;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import net.sf.json.JSONObject;
public class SearchFromES {
//	List<Object> belongTos;
//	List<Object> IDs;
	List<JSONObject> contents = new ArrayList<JSONObject>();
	int totals;
    int numPages;
    int numUsers;

	public void search(int type, List<String> tags, int pageId, int pageSize, int platform) throws UnknownHostException{


//		belongTos = new ArrayList();
//		IDs = new ArrayList();
        StringBuilder queryString = new StringBuilder();
		boolean first = true;

		for (String tag : tags) {
			if (first) {
				first = false;
				queryString.append(tag);
			} else {
				queryString.append(" ").append(tag);
			}
		}

//		Settings settings = Settings.builder()
//		        .put("cluster.name", "sdpes")
//		        .build();
//
//		TransportClient client = new PreBuiltTransportClient(settings)
//			.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.7.101"), 9300))
//			.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.7.102"), 9300))
//			.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.7.103"), 9300))
//			.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.7.104"), 9300));
		TransportClient client = ESConnection.getConnection();
		MultiMatchQueryBuilder qb;
		if(type == 0){// search according to profile
			qb = multiMatchQuery(
                    queryString.toString(),
				    "profile.name", "profile.country", "profile.mail", "profile.address", "profile.aboutMe"
				);//.fuzziness("AUTO");
//			qb.type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
//				.field("profile.name", 3.0f)
//				.field("profile.url", 1.0f)
//				.field("profile.mail", 1.0f);
		}
		else { // according to pl, others, document
			qb = multiMatchQuery(
                    queryString.toString(),
				    "skills.tag.pl", "skills.tag.others", "skills.document"       
				);
			qb.type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
				.field("skills.tag.pl", 3.0f)
				.field("skills.tag.others", 2.0f)
				.field("skills.document", 1.0f);
		}

//		QueryBuilder qb_;
//		if(platform != -1) {
//			qb_ = boolQuery()
//					.must(qb)
//					.filter(termQuery("profile.belongTo", platform));
//		}
//		else{
//			qb_ = boolQuery()
//					.must(qb);
//		}
        SearchResponse response;
		if(platform == -1) {

            response = client.prepareSearch("github", "topcoder", "so", "csdn")// topcoder
                    .setTypes("user")//
//		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(qb)                                                       // Query
//		        .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
                    .setFrom((pageId-1)*pageSize).setSize(pageSize)
                    .setFetchSource(null, new String[]{"skills.document"})
//		        .setExplain(true)
                    .get();
        }
        else {
		    String platformString;
		    if(platform == 0) platformString = "github";
		    else if(platform == 1) platformString = "topcoder";
		    else if(platform == 2) platformString = "so";
		    else platformString = "csdn";


            response = client.prepareSearch(platformString)// topcoder
//                    .setTypes(platformString)//
//		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(qb)                                                       // Query
//		        .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
                    .setFrom((pageId-1)*pageSize).setSize(pageSize)
                    .setFetchSource(null, new String[]{"skills.document"})
//		        .setExplain(true)
                    .get();

        }

        this.totals = (int)response.getHits().getTotalHits();
        this.numUsers = this.totals;
        this.numPages = (this.numUsers + pageSize - 1) / pageSize;
//		System.out.println(totals);
		for (SearchHit hit : response.getHits().getHits()) {
//			System.out.println(hit.getSourceAsString());
//			System.out.println(hit.getSource().get("userID"));
//			System.out.println(hit.getSource().get("profile"));
			JSONObject json = JSONObject.fromObject(hit.getSourceAsString());
			String index = hit.getIndex();
//			System.out.println(index);
			if(index.equals("github")){
//				System.out.println("yes");
				JSONObject profile = json.getJSONObject("profile");
				profile.put("belongTo", 0);
				json.remove("profile");
				json.put("profile", profile);
			}
//			System.out.println(json);
			contents.add(json);
//			JSONObject json = JSONObject.fromObject(hit.getSource().get("profile"));
//			int belongTo = json.getInt("belongTo");
//			this.belongTos.add(belongTo);
//			if(belongTo == 1) {
//				// so using userID as ID
//				int ID = (int) hit.getSource().get("userID");
//				this.IDs.add(ID);
//			}
//			else {
//				// github stackoverflow using login/handle as ID
//				String name = json.getString("name");
//				this.IDs.add(name);
//			}
			
		}

//		client.close();
	}
//	public List getBelongTos(){
//		return this.belongTos;
//	}
//	public List getIDs(){
//		return this.IDs;
//	}
//	public int getTotals(){
//		return this.totals;
//	}
	public List getContents(){
		return this.contents;
	}
	public int getNumPages() {
	    return numPages;
    }
    public int getNumUsers() {
	    return numUsers;
    }
	
//	public static void main(String[] args) throws UnknownHostException {
//		SearchFromES s = new SearchFromES();
//		List<String> x = new ArrayList<String>();
//		x.add("java");
//		x.add("c");
//		s.search(x, 1, 10);
//		for(int i = 0; i < s.belongTos.size(); i++){
//			System.out.println(s.belongTos.get(i));
//			System.out.println(s.IDs.get(i));
//		}
//		System.out.println(s.totals);
//	}
}
