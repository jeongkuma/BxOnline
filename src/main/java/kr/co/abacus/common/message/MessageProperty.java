package kr.co.abacus.common.message;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "mymsg")
public class MessageProperty {
    @NestedConfigurationProperty
    private List<String> messageList = new ArrayList<>();

    public List<String> getMessageList() {
        return messageList;
    }

}
