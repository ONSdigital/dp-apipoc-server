package uk.gov.ons.hornet.dependency;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * Created by aggos on 26/01/2017.
 */
public abstract class FakeElasticSearch {
    private final String HTTP_PORT = "9200";
    private final String HTTP_TRANSPORT_PORT = "9305";
    private final String ES_WORKING_DIR = "target/es";

    private Node node;

    public void startElasticsearch() throws Exception {
//        removeOldDataDir(ES_WORKING_DIR + "/" + clusterName);

        Settings settings = Settings.builder()
                .put("path.home", ES_WORKING_DIR)
                .put("path.conf", ES_WORKING_DIR)
                .put("path.data", ES_WORKING_DIR)
                .put("path.work", ES_WORKING_DIR)
                .put("path.logs", ES_WORKING_DIR)
                .put("http.port", HTTP_PORT)
                .put("transport.tcp.port", HTTP_TRANSPORT_PORT)
                .put("index.number_of_shards", "1")
                .put("index.number_of_replicas", "0")
                .put("discovery.zen.ping.multicast.enabled", "false")
                .build();
        node = nodeBuilder().settings(settings).clusterName("monkeys.health").client(false).node();
        node.start();
    }

    public void stopElasticsearch() {
        node.close();
    }
/*
    private static void removeOldDataDir(String datadir) throws Exception {
        File dataDir = new File(datadir);
        if (dataDir.exists()) {
            FileSystemUtils.deleteRecursively(dataDir);
        }
    }*/
}