package org.engineered.prefixsearch;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.JsonElement;
import org.engineered.prefixsearch.searchindex.SearchIndex;

import java.util.HashMap;
import java.util.List;

public class SearchHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Invoking Function " + context.getFunctionName());

        String searchKey = event.getQueryStringParameters().get("q");
        logger.log("Search Key: "+ searchKey);

        logger.log("Loading Search Index....");
        SearchIndex searchIndex = new SearchIndex();
        searchIndex.loadIndex();
        logger.log("Loading Search Index Successful");

        logger.log("Building Search Prefix Tree....");
        PrefixSearchTree prefixSearchTree = new PrefixSearchTree();
        prefixSearchTree.insertKeys(searchIndex.getIndexKeys());
        logger.log("Building Search Prefix Tree Completed");

        logger.log("Searching For Matches...");
        PrefixSearchTreeTraverser prefixSearchTreeTraverser = new PrefixSearchTreeTraverser();
        List<String> searchResults = prefixSearchTreeTraverser
                .search(searchKey, prefixSearchTree.getSearchTreeRootNode());
        logger.log("Search Completed");

        List<JsonElement> results = searchIndex.getSearchSuggestionURLS(searchResults);

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setIsBase64Encoded(false);
        response.setStatusCode(200);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        response.setHeaders(headers);
        response.setBody(results.toString());
        return response;
    }
}