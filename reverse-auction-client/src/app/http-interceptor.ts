import { Injectable, Injector } from '@angular/core';
import {
    HttpEvent,
    HttpInterceptor,
    HttpHandler,
    HttpRequest
} from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/observable/throw'
import 'rxjs/add/operator/catch';


@Injectable()
export class CustomHttpInterceptor implements HttpInterceptor {
    constructor() { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        let userStorage = localStorage.getItem("auction-user");
        let user = JSON.parse(userStorage);
        if (user && user.token) {
            console.log("intercepted request ... ");

            const authReq = req.clone({ headers: req.headers.set("X-AUTH-TOKEN", user.token) });

            console.log("Sending request with new header now ...");


            return next.handle(authReq)
                .catch((error, caught) => {
                    console.log("Error Occurred");
                    console.log(error);
                    return Observable.throw(error);
                }) as any;
        }

        return next.handle(req);
    }
}