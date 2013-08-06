//
//  iOSRequest.m
//  MapViews
//
//  Created by Sankha Subhra Pathak on 8/1/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import "iOSRequest.h"
#import "NSString+WebService.h"
@implementation iOSRequest

+(void)requestRESTPOST:(NSString *)path withParams:(NSMutableDictionary *)params
          onCompletion:(RequestCompletionHandler)complete{
    // Background Queue
    NSOperationQueue *backgroundQueue = [[NSOperationQueue alloc] init];
    
    // URL Request
    NSMutableURLRequest     *request = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:path]
                                                                    cachePolicy:NSURLCacheStorageAllowedInMemoryOnly
                                                                timeoutInterval:10];
    
    [request setHTTPMethod:@"POST"];
    NSString *postString = [[NSString alloc] init];
    NSLog(@"%d", params.count);
    for (id key in params){
        NSString *value = [params objectForKey:key];
        NSLog(@"%@", value);
        postString = [postString stringByAppendingString:@"&"];
        postString = [postString stringByAppendingString:key];
        postString = [postString stringByAppendingString:@"="];
        postString = [postString stringByAppendingString:value];
    }
    if(nil != postString && postString.length>0)
    {
        postString=[postString substringWithRange:NSMakeRange(1, postString.length-1)];
    }
    
    [request setHTTPBody:[postString dataUsingEncoding:NSUTF8StringEncoding]];
    
    // Send Request
    [NSURLConnection sendAsynchronousRequest:request
                                       queue:backgroundQueue
                           completionHandler:^(NSURLResponse *response, NSData *data, NSError *error){
                               NSString *result = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
                               if (complete) complete(result,error);
                           }];
}

+(void)requestRESTGET:(NSString *)path
         onCompletion:(RequestCompletionHandler)complete{
    // Background Queue
    NSOperationQueue *backgroundQueue = [[NSOperationQueue alloc] init];
    
    // URL Request
    NSURLRequest *request = [[NSURLRequest alloc] initWithURL:[NSURL URLWithString:path]
                                                  cachePolicy:NSURLCacheStorageAllowedInMemoryOnly
                                              timeoutInterval:10];
    
    // Send Request
    [NSURLConnection sendAsynchronousRequest:request
                                       queue:backgroundQueue
                           completionHandler:^(NSURLResponse *response, NSData *data, NSError *error){
                               NSString *result = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
                               if (complete) complete(result,error);
                           }];
}


+(void)refreshNotifications:(NSString *)userEmail andLastRefreshId:(NSString *)lastRefreshId onCompletion:(RequestDictionaryCompletionHandler)complete{
    //userEmail = [userEmail URLEncode];
    
    NSString *basePath = kgetNotificationURL;
    NSString *fullPath = [basePath stringByAppendingFormat:@"%@/%@",userEmail,lastRefreshId];
    
    NSLog(@"%@",fullPath);
    [iOSRequest requestRESTGET:fullPath onCompletion:^(NSString *result, NSError *error){
        if (error || [result isEqualToString:@""]) {
            if (complete) complete(nil);
        } else {
            
            NSLog(@"---------------------> %@",result);
            
            NSDictionary *user = [result JSON];
            NSLog(@"---------------------> %@",user);
            if (complete) complete(user);
        }
    }];
}

+(void)refreshMessages:(NSString *)userEmail andLastRefreshId:(NSString *)lastRefreshId onCompletion:(RequestDictionaryCompletionHandler)complete{
    //userEmail = [userEmail URLEncode];
    
    NSString *basePath = kgetMessagesURL;
    NSString *fullPath = [basePath stringByAppendingFormat:@"/%@/%@",userEmail,lastRefreshId];
    
    NSLog(@"%@",fullPath);
    [iOSRequest requestRESTGET:fullPath onCompletion:^(NSString *result, NSError *error){
        if (error || [result isEqualToString:@""]) {
            if (complete) complete(nil);
        } else {
            
            NSLog(@"%@",result);
            
            NSDictionary *user = [result JSON];
            NSLog(@"%@",user);
            if (complete) complete(user);
        }
    }];
}
+(void) getLastRequestByUser:(NSString *)userEmail{
    GlobalSettings = [[NSMutableArray alloc] init];
    
    NSString *basePath = kgetLastRequestByUserURL;
    NSString *fullPath = [basePath stringByAppendingFormat:@"/%@",userEmail];
    
    NSLog(@"%@",fullPath);
    
    NSURLRequest *request = [[NSURLRequest alloc] initWithURL:[NSURL URLWithString:fullPath]
                                                  cachePolicy:NSURLCacheStorageAllowedInMemoryOnly
                                              timeoutInterval:10];
    
    
    NSError        *error = nil;
    NSURLResponse  *response = nil;
    
    
    NSData *data =  [NSURLConnection sendSynchronousRequest: request returningResponse: &response error: &error];
    
    NSString* stringReply = (NSString *)[[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
    
    if (!error) {
        NSLog(@"SUCCESS : %@", stringReply );
        @try {
            [GlobalSettings insertObject:stringReply atIndex:kRequestId];
            [GlobalSettings insertObject:@"0" atIndex:kLastRefreshId];
            NSLog(@"%@",GlobalSettings);
        }
        @catch (NSException *exception) {
            NSLog(@"Exception : %@", exception);
            [GlobalSettings insertObject:@"0" atIndex:kRequestId];
            [GlobalSettings insertObject:@"0" atIndex:kLastRefreshId];
        }
    } else {
        [GlobalSettings insertObject:@"0" atIndex:kRequestId];
        [GlobalSettings insertObject:@"0" atIndex:kLastRefreshId];
    }
    
}
@end
