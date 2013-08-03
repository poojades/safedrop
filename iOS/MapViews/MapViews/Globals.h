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

#define kRequesterUsername @"sankhasp@cmu.edu"

#define kGetNotificationURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/getNotifications/";

#define kSetUserInfoURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/setUserInfo"


#define kRequestPickupURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/requestPickup"

#define kRequestStatusURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/getRequestStatus"

#define kAcceptRequestURL @"http://128.2.204.85:6080/SafeDropServices/rest/service/acceptVolunteer"

#endif
