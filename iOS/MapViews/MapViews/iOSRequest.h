//
//  iOSRequest.h
//  MapViews
//
//  Created by Sankha Subhra Pathak on 8/1/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Globals.h"
typedef void(^RequestCompletionHandler)(NSString *, NSError *);
typedef void(^RequestDictionaryCompletionHandler)(NSDictionary*);
@interface iOSRequest : NSObject


+(void)requestRESTPOST:(NSString *)path withParams:(NSMutableDictionary *)params
      onCompletion:(RequestCompletionHandler)complete;

+(void)requestRESTGET:(NSString *)path
          onCompletion:(RequestCompletionHandler)complete;

+(void)refreshNotifications:(NSString *)userEmail andLastRefreshId:(NSString *)lastRefreshId onCompletion:(RequestDictionaryCompletionHandler)complete;
@end
