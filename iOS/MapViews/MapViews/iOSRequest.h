//
//  iOSRequest.h
//  MapViews
//
//  Created by Sankha Subhra Pathak on 8/1/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef void(^RequestCompletionHandler)(NSString *, NSError *);
@interface iOSRequest : NSObject


+(void)requestREST:(NSString *)path withParams:(NSMutableDictionary *)params
      onCompletion:(RequestCompletionHandler)complete;


@end
