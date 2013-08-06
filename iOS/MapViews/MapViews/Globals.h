//
//  Globals.h
//  MapViews
//
//  Created by Sankha Subhra Pathak on 8/3/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#ifndef MapViews_Globals_h
#define MapViews_Globals_h

NSMutableArray * GlobalSettings;

#define kRequestId 0
#define kLastRefreshId 1
#define kOtherUserId 2
#define kEmergency 3


typedef enum  {
    NotCreated,
    New,
    Archived,
    Done,
    Cancel,
    Pending,
    InProgress,
    Accepted
} RequestStatus;

RequestStatus status;

#define kRequesterUsername @"sankhasp1@cmu.edu"


#define kgetNotificationURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/getNotifications/";

#define ksetUserInfoURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/setUserInfo"

#define kgetUserInfoURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/getUserInfo"

#define krequestPickupURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/requestPickup"

#define krequestStatusURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/getRequestStatus"

#define kacceptVolunteerURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/acceptVolunteer"

#define kacceptRequesterURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/acceptRequester"

#define kgetLastRequestByUserURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/getLastRequestByUser"

#define kgetOtherUserInfoURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/getOtherUserInfo"

#define kgetRatingsURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/getRatings"

#define kaddRatingsURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/addRating"

#define kcloseRequestURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/closeRequest"

#define kgetMessagesURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/getMessages"

#define ksendMessageURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/sendMessage"


#endif
