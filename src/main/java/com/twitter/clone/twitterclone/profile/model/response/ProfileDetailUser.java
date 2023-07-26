<<<<<<< HEAD:src/main/java/com/twitter/clone/twitterclone/profile/model/response/ProfileDetailUser.java
package com.twitter.clone.twitterclone.profile.model.response;
=======
package com.twitter.clone.twitterclone.profile.model.Response;
>>>>>>> master:src/main/java/com/twitter/clone/twitterclone/profile/model/Response/ProfileDetailUser.java

import java.time.LocalDateTime;
import java.util.List;

public record ProfileDetailUser (
        String nickname,
        String tagName,
        String profileImageUrl,
        String profileBackgroundImageUrl,
        String content,
        String url,
        LocalDateTime createdAt,
        List<UserTweetsResponse> myList
){

}
