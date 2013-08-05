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

//position 0 -> requestId

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


#define kGetNotificationURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/getNotifications/";

#define kSetUserInfoURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/setUserInfo"

#define kGetUserInfoURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/getUserInfo"

#define kRequestPickupURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/requestPickup"

#define kRequestStatusURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/getRequestStatus"

#define kAcceptVolunteerURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/acceptVolunteer"

#define kAcceptRequesterURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/acceptRequester"

#define kgetLastRequestByUserURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/getLastRequestByUser"

#define kgetOtherUserInfoURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/getOtherUserInfo"


#endif
