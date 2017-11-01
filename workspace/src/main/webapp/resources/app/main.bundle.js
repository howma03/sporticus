webpackJsonp([1, 4], {

    /***/ 290: /***/ (function (module, exports) {

        function webpackEmptyContext(req) {
            throw new Error("Cannot find module '" + req + "'.");
        }

        webpackEmptyContext.keys = function () {
            return [];
        };
        webpackEmptyContext.resolve = webpackEmptyContext;
        module.exports = webpackEmptyContext;
        webpackEmptyContext.id = 290;


        /***/
    }),

    /***/ 291: /***/ (function (module, __webpack_exports__, __webpack_require__) {

        "use strict";
        Object.defineProperty(__webpack_exports__, "__esModule", {value: true});
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__ = __webpack_require__(378);
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_2__app_app_module__ = __webpack_require__(400);
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_3__environments_environment__ = __webpack_require__(409);


        if (__WEBPACK_IMPORTED_MODULE_3__environments_environment__["a" /* environment */].production) {
            __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["a" /* enableProdMode */])();
        }
        __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_2__app_app_module__["a" /* AppModule */]);
//# sourceMappingURL=main.js.map

        /***/
    }),

    /***/ 398: /***/ (function (module, __webpack_exports__, __webpack_require__) {

        "use strict";
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
        /* harmony export (binding) */
        __webpack_require__.d(__webpack_exports__, "a", function () {
            return AdminLinkDirective;
        });
        var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
                var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
                if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
                else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
                return c > 3 && r && Object.defineProperty(target, key, r), r;
            };
        var __metadata = (this && this.__metadata) || function (k, v) {
                if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
            };

        var AdminLinkDirective = (function () {
            function AdminLinkDirective() {
            }

            AdminLinkDirective = __decorate([
                __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["v" /* Directive */])({
                    selector: '[appAdminLink]'
                }),
                __metadata('design:paramtypes', [])
            ], AdminLinkDirective);
            return AdminLinkDirective;
        }());
//# sourceMappingURL=management-link.directive.js.map

        /***/
    }),

    /***/ 399: /***/ (function (module, __webpack_exports__, __webpack_require__) {

        "use strict";
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
        /* harmony export (binding) */
        __webpack_require__.d(__webpack_exports__, "a", function () {
            return AppComponent;
        });
        var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
                var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
                if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
                else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
                return c > 3 && r && Object.defineProperty(target, key, r), r;
            };
        var __metadata = (this && this.__metadata) || function (k, v) {
                if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
            };

        var AppComponent = (function () {
            function AppComponent() {
                this.title = 'app works!';
            }

            AppComponent.prototype.onColor = function (color) {
                console.log("App - Color:", color);
            };
            AppComponent.prototype.reset = function () {
                console.log("App - Reset");
            };
            AppComponent = __decorate([
                __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["U" /* Component */])({
                    selector: 'app-root',
                    template: __webpack_require__(471),
                    styles: [__webpack_require__(463)]
                }),
                __metadata('design:paramtypes', [])
            ], AppComponent);
            return AppComponent;
        }());
//# sourceMappingURL=app.component.js.map

        /***/
    }),

    /***/ 400: /***/ (function (module, __webpack_exports__, __webpack_require__) {

        "use strict";
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__ = __webpack_require__(166);
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_1__angular_core__ = __webpack_require__(0);
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__(369);
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_3__angular_http__ = __webpack_require__(256);
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_4__app_component__ = __webpack_require__(399);
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_5__site_header_site_header_component__ = __webpack_require__(406);
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_6__admin_link_directive__ = __webpack_require__(398);
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_7__task_api_service__ = __webpack_require__(407);
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_8__task_search_box_task_search_box_component__ = __webpack_require__(408);
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_9__color_picker_color_picker_component__ = __webpack_require__(401);
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_10__color_previewer_color_previewer_component__ = __webpack_require__(403);
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_11__color_sample_color_sample_component__ = __webpack_require__(404);
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_12__hero_table_hero_table_component__ = __webpack_require__(405);
        /* harmony export (binding) */
        __webpack_require__.d(__webpack_exports__, "a", function () {
            return AppModule;
        });
        var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
                var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
                if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
                else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
                return c > 3 && r && Object.defineProperty(target, key, r), r;
            };
        var __metadata = (this && this.__metadata) || function (k, v) {
                if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
            };


        var AppModule = (function () {
            function AppModule() {
            }

            AppModule = __decorate([
                __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__angular_core__["b" /* NgModule */])({
                    declarations: [
                        __WEBPACK_IMPORTED_MODULE_4__app_component__["a" /* AppComponent */],
                        __WEBPACK_IMPORTED_MODULE_5__site_header_site_header_component__["a" /* SiteHeaderComponent */],
                        __WEBPACK_IMPORTED_MODULE_6__admin_link_directive__["a" /* AdminLinkDirective */],
                        __WEBPACK_IMPORTED_MODULE_8__task_search_box_task_search_box_component__["a" /* TaskSearchBoxComponent */],
                        __WEBPACK_IMPORTED_MODULE_9__color_picker_color_picker_component__["a" /* ColorPickerComponent */],
                        __WEBPACK_IMPORTED_MODULE_10__color_previewer_color_previewer_component__["a" /* ColorPreviewerComponent */],
                        __WEBPACK_IMPORTED_MODULE_11__color_sample_color_sample_component__["a" /* ColorSampleComponent */],
                        __WEBPACK_IMPORTED_MODULE_12__hero_table_hero_table_component__["a" /* HeroTableComponent */]
                    ],
                    imports: [
                        __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__["a" /* BrowserModule */],
                        __WEBPACK_IMPORTED_MODULE_2__angular_forms__["a" /* FormsModule */],
                        __WEBPACK_IMPORTED_MODULE_3__angular_http__["a" /* HttpModule */]
                    ],
                    providers: [__WEBPACK_IMPORTED_MODULE_7__task_api_service__["a" /* TaskApiService */]],
                    bootstrap: [__WEBPACK_IMPORTED_MODULE_4__app_component__["a" /* AppComponent */]]
                }),
                __metadata('design:paramtypes', [])
            ], AppModule);
            return AppModule;
        }());
//# sourceMappingURL=app.module.js.map

        /***/
    }),

    /***/ 401: /***/ (function (module, __webpack_exports__, __webpack_require__) {

        "use strict";
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_1__constants__ = __webpack_require__(402);
        /* harmony export (binding) */
        __webpack_require__.d(__webpack_exports__, "a", function () {
            return ColorPickerComponent;
        });
        var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
                var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
                if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
                else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
                return c > 3 && r && Object.defineProperty(target, key, r), r;
            };
        var __metadata = (this && this.__metadata) || function (k, v) {
                if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
            };


        var ColorPickerComponent = (function () {
            function ColorPickerComponent() {
                this.colorOutput = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["G" /* EventEmitter */]();
            }

            ColorPickerComponent.prototype.ngOnInit = function () {
            };
            ColorPickerComponent.prototype.choose = function (color) {
                console.log("color-picker - Color clicked .. color=" + color);
                this.colorOutput.emit(color);
            };
            ColorPickerComponent.prototype.reset = function () {
                console.log("color-picker - Color reset");
                this.colorOutput.emit("black");
            };
            __decorate([
                __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["w" /* Input */])(),
                __metadata('design:type', String)
            ], ColorPickerComponent.prototype, "color", void 0);
            __decorate([
                __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["T" /* Output */])("color"),
                __metadata('design:type', Object)
            ], ColorPickerComponent.prototype, "colorOutput", void 0);
            ColorPickerComponent = __decorate([
                __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["U" /* Component */])({
                    selector: 'color-picker',
                    template: __webpack_require__(472),
                    template: "\n    \n    <div class=\"color-title\" [ngStyle]=\"{color:color}\">Pick a Color:</div>  \n    \n    <div class=\"color-picker\">\n        <div class=\"color-sample color-sample-blue\" (click)=\"choose('" + __WEBPACK_IMPORTED_MODULE_1__constants__["a" /* BLUE */] + "')\"></div>  \n        <div class=\"color-sample color-sample-red\" (click)=\"choose('" + __WEBPACK_IMPORTED_MODULE_1__constants__["b" /* RED */] + "')\"></div>                \n    </div>\n    <button (click)=\"reset()\">Reset</button>\n    ",
                    styles: [__webpack_require__(464)]
                }),
                __metadata('design:paramtypes', [])
            ], ColorPickerComponent);
            return ColorPickerComponent;
        }());
//# sourceMappingURL=color-picker.component.js.map

        /***/
    }),

    /***/ 402: /***/ (function (module, __webpack_exports__, __webpack_require__) {

        "use strict";
        /* harmony export (binding) */
        __webpack_require__.d(__webpack_exports__, "a", function () {
            return BLUE;
        });
        /* harmony export (binding) */
        __webpack_require__.d(__webpack_exports__, "b", function () {
            return RED;
        });
        var BLUE = '#0000ff';
        var RED = '#ff0000';
//# sourceMappingURL=constants.js.map

        /***/
    }),

    /***/ 403: /***/ (function (module, __webpack_exports__, __webpack_require__) {

        "use strict";
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
        /* harmony export (binding) */
        __webpack_require__.d(__webpack_exports__, "a", function () {
            return ColorPreviewerComponent;
        });
        var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
                var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
                if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
                else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
                return c > 3 && r && Object.defineProperty(target, key, r), r;
            };
        var __metadata = (this && this.__metadata) || function (k, v) {
                if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
            };

        var ColorPreviewerComponent = (function () {
            function ColorPreviewerComponent() {
            }

            ColorPreviewerComponent.prototype.ngOnInit = function () {
            };
            __decorate([
                __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["w" /* Input */])('color'),
                __metadata('design:type', String)
            ], ColorPreviewerComponent.prototype, "color", void 0);
            ColorPreviewerComponent = __decorate([
                __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["U" /* Component */])({
                    selector: 'app-color-previewer',
                    template: __webpack_require__(473),
                    styles: [__webpack_require__(465)]
                }),
                __metadata('design:paramtypes', [])
            ], ColorPreviewerComponent);
            return ColorPreviewerComponent;
        }());
//# sourceMappingURL=color-previewer.component.js.map

        /***/
    }),

    /***/ 404: /***/ (function (module, __webpack_exports__, __webpack_require__) {

        "use strict";
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
        /* harmony export (binding) */
        __webpack_require__.d(__webpack_exports__, "a", function () {
            return ColorSampleComponent;
        });
        var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
                var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
                if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
                else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
                return c > 3 && r && Object.defineProperty(target, key, r), r;
            };
        var __metadata = (this && this.__metadata) || function (k, v) {
                if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
            };

        var ColorSampleComponent = (function () {
            function ColorSampleComponent() {
            }

            ColorSampleComponent.prototype.ngOnInit = function () {
            };
            __decorate([
                __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["w" /* Input */])(),
                __metadata('design:type', String)
            ], ColorSampleComponent.prototype, "color", void 0);
            ColorSampleComponent = __decorate([
                __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["U" /* Component */])({
                    selector: 'app-color-sample',
                    template: __webpack_require__(474),
                    styles: [__webpack_require__(466)]
                }),
                __metadata('design:paramtypes', [])
            ], ColorSampleComponent);
            return ColorSampleComponent;
        }());
//# sourceMappingURL=color-sample.component.js.map

        /***/
    }),

    /***/ 405: /***/ (function (module, __webpack_exports__, __webpack_require__) {

        "use strict";
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
        /* harmony export (binding) */
        __webpack_require__.d(__webpack_exports__, "a", function () {
            return HeroTableComponent;
        });
        var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
                var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
                if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
                else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
                return c > 3 && r && Object.defineProperty(target, key, r), r;
            };
        var __metadata = (this && this.__metadata) || function (k, v) {
                if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
            };

        var HeroTableComponent = (function () {
            function HeroTableComponent() {
            }

            HeroTableComponent.prototype.ngOnInit = function () {
            };
            HeroTableComponent = __decorate([
                __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["U" /* Component */])({
                    selector: 'app-hero-table',
                    template: __webpack_require__(475),
                    styles: [__webpack_require__(467)]
                }),
                __metadata('design:paramtypes', [])
            ], HeroTableComponent);
            return HeroTableComponent;
        }());
//# sourceMappingURL=hero-table.component.js.map

        /***/
    }),

    /***/ 406: /***/ (function (module, __webpack_exports__, __webpack_require__) {

        "use strict";
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
        /* harmony export (binding) */
        __webpack_require__.d(__webpack_exports__, "a", function () {
            return SiteHeaderComponent;
        });
        var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
                var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
                if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
                else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
                return c > 3 && r && Object.defineProperty(target, key, r), r;
            };
        var __metadata = (this && this.__metadata) || function (k, v) {
                if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
            };

        var SiteHeaderComponent = (function () {
            function SiteHeaderComponent() {
            }

            SiteHeaderComponent.prototype.ngOnInit = function () {
            };
            SiteHeaderComponent = __decorate([
                __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["U" /* Component */])({
                    selector: 'app-site-header',
                    template: __webpack_require__(476),
                    styles: [__webpack_require__(468)]
                }),
                __metadata('design:paramtypes', [])
            ], SiteHeaderComponent);
            return SiteHeaderComponent;
        }());
//# sourceMappingURL=site-header.component.js.map

        /***/
    }),

    /***/ 407: /***/ (function (module, __webpack_exports__, __webpack_require__) {

        "use strict";
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__(256);
        /* harmony export (binding) */
        __webpack_require__.d(__webpack_exports__, "a", function () {
            return TaskApiService;
        });
        var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
                var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
                if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
                else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
                return c > 3 && r && Object.defineProperty(target, key, r), r;
            };
        var __metadata = (this && this.__metadata) || function (k, v) {
                if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
            };


        var TaskApiService = (function () {
            // TS shortcut "public" to put http on this
            function TaskApiService(http) {
                this.http = http;
            }

            TaskApiService.prototype.snapshot = function (symbols) {
                var params = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["b" /* URLSearchParams */]();
                params.set('symbols', symbols);
                return this.http.get("/api/task", {search: params});
                // .map(res => res.json()) // convert to JSON
                // .map(x => x.filter(y => y.name)); // Remove stocks w/no name
            };
            TaskApiService = __decorate([
                __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["c" /* Injectable */])(),
                __metadata('design:paramtypes', [(typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_http__["c" /* Http */] !== 'undefined' && __WEBPACK_IMPORTED_MODULE_1__angular_http__["c" /* Http */]) === 'function' && _a) || Object])
            ], TaskApiService);
            return TaskApiService;
            var _a;
        }());
//# sourceMappingURL=task-api.services.js.map

        /***/
    }),

    /***/ 408: /***/ (function (module, __webpack_exports__, __webpack_require__) {

        "use strict";
        /* harmony import */
        var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
        /* harmony export (binding) */
        __webpack_require__.d(__webpack_exports__, "a", function () {
            return TaskSearchBoxComponent;
        });
        var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
                var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
                if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
                else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
                return c > 3 && r && Object.defineProperty(target, key, r), r;
            };
        var __metadata = (this && this.__metadata) || function (k, v) {
                if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
            };

        var TaskSearchBoxComponent = (function () {
            function TaskSearchBoxComponent() {
                this.placeholder = "Type in your search";
            }

            TaskSearchBoxComponent.prototype.ngOnInit = function () {
            };
            TaskSearchBoxComponent.prototype.find = function (input) {
                console.log("Find clicked .. input=" + input);
            };
            TaskSearchBoxComponent.prototype.clear = function (input) {
                console.log("Clear clicked .. input=" + input);
                input.value = '';
            };
            __decorate([
                __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["w" /* Input */])('placeholderText'),
                __metadata('design:type', Object)
            ], TaskSearchBoxComponent.prototype, "placeholder", void 0);
            TaskSearchBoxComponent = __decorate([
                __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["U" /* Component */])({
                    selector: 'task-search-box',
                    template: __webpack_require__(477),
                    styles: [__webpack_require__(469)]
                }),
                __metadata('design:paramtypes', [])
            ], TaskSearchBoxComponent);
            return TaskSearchBoxComponent;
        }());
//# sourceMappingURL=task-search-box.component.js.map

        /***/
    }),

    /***/ 409: /***/ (function (module, __webpack_exports__, __webpack_require__) {

        "use strict";
        /* harmony export (binding) */
        __webpack_require__.d(__webpack_exports__, "a", function () {
            return environment;
        });
// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.
        var environment = {
            production: false
        };
//# sourceMappingURL=environment.js.map

        /***/
    }),

    /***/ 463: /***/ (function (module, exports, __webpack_require__) {

        exports = module.exports = __webpack_require__(27)();
// imports


// module
        exports.push([module.i, "", ""]);

// exports


        /*** EXPORTS FROM exports-loader ***/
        module.exports = module.exports.toString();

        /***/
    }),

    /***/ 464: /***/ (function (module, exports, __webpack_require__) {

        exports = module.exports = __webpack_require__(27)();
// imports


// module
        exports.push([module.i, ".color-sample {\r\n  width: 50px;\r\n  height: 50px;\r\n  border: 1px solid #000;\r\n}\r\n\r\n.lesson {\r\n  text-align: center;\r\n  max-width: 330px;\r\n}\r\n\r\n.color-picker {\r\n\r\n}\r\n\r\n.color-title {\r\n  text-decoration: underline;\r\n  font-weight: bold;\r\n  margin-bottom: 10px;\r\n}\r\n\r\n.color-sample {\r\n  height: 50px;\r\n  width: 50px;\r\n  border: 1px solid gray;\r\n  display: inline-block;\r\n  border-radius: 4px;\r\n  margin-right: 10px;\r\n  cursor: pointer;\r\n}\r\n\r\n.color-sample-blue {\r\n  background: #1976d2;\r\n\r\n}\r\n\r\n.color-sample-red {\r\n  background: #b13138;\r\n}\r\n\r\n.color-previewer {\r\n  margin-top: 50px;\r\n  margin-bottom: 50px;\r\n  font-weight: bold;\r\n  font-size: 21px;\r\n  text-transform: uppercase;\r\n\r\n}\r\n\r\n\r\n", ""]);

// exports


        /*** EXPORTS FROM exports-loader ***/
        module.exports = module.exports.toString();

        /***/
    }),

    /***/ 465: /***/ (function (module, exports, __webpack_require__) {

        exports = module.exports = __webpack_require__(27)();
// imports


// module
        exports.push([module.i, "", ""]);

// exports


        /*** EXPORTS FROM exports-loader ***/
        module.exports = module.exports.toString();

        /***/
    }),

    /***/ 466: /***/ (function (module, exports, __webpack_require__) {

        exports = module.exports = __webpack_require__(27)();
// imports


// module
        exports.push([module.i, "", ""]);

// exports


        /*** EXPORTS FROM exports-loader ***/
        module.exports = module.exports.toString();

        /***/
    }),

    /***/ 467: /***/ (function (module, exports, __webpack_require__) {

        exports = module.exports = __webpack_require__(27)();
// imports


// module
        exports.push([module.i, "", ""]);

// exports


        /*** EXPORTS FROM exports-loader ***/
        module.exports = module.exports.toString();

        /***/
    }),

    /***/ 468: /***/ (function (module, exports, __webpack_require__) {

        exports = module.exports = __webpack_require__(27)();
// imports


// module
        exports.push([module.i, "", ""]);

// exports


        /*** EXPORTS FROM exports-loader ***/
        module.exports = module.exports.toString();

        /***/
    }),

    /***/ 469: /***/ (function (module, exports, __webpack_require__) {

        exports = module.exports = __webpack_require__(27)();
// imports


// module
        exports.push([module.i, ".btn {\r\n  font-weight: bold;\r\n  border-radius: 4px;\r\n}\r\n\r\n.btn-find {\r\n  background: #00ff3f;\r\n  color: white;\r\n}\r\n\r\n.btn-clear {\r\n  background: #ff00aa;\r\n  color: white;\r\n}\r\n", ""]);

// exports


        /*** EXPORTS FROM exports-loader ***/
        module.exports = module.exports.toString();

        /***/
    }),

    /***/ 471: /***/ (function (module, exports) {

        module.exports = "<h1>\n  {{title}}\n</h1>\n<hr/>\n<div>\n\n  <div class=\"well\">\n    <task-search-box placeholderText=\"Some Text 2\"></task-search-box>\n    <task-search-box placeholderText=\"Some Text 3\"></task-search-box>\n    <task-search-box placeholderText=\"Some Text 4\"></task-search-box>\n    <task-search-box placeholderText=\"Some Text 5\"></task-search-box>\n  </div>\n\n  <div class=\"well\">\n    <color-picker #picker color=\"green\" (color)=\"previewer.color = $event\"></color-picker>\n    <button (click)=\"picker.reset()\">Reset</button>\n    <button (click)=\"reset()\">App Reset</button>\n    <app-color-previewer #previewer color=\"red\"></app-color-previewer>\n  </div>\n\n  <hr/>\n\n  <div class=\"well\">\n    <app-color-sample #sample></app-color-sample>\n    <input (keyup)=\"sample.color = input.value\" #input placeholder=\"Type your color\"/>\n  </div>\n</div>\n"

        /***/
    }),

    /***/ 472: /***/ (function (module, exports) {

        module.exports = "<div class=\"well\">\n  <div class=\"color-picker-title\" [ngStyle]=\"{'color':color}\">Pick a Color</div>\n\n  <div class=\"color-picker\">\n    <div class=\"color-sample color-sample-blue\" (click)=\"choose('${BLUE}')\"></div>\n    <div class=\"color-sample color-sample-red\" (click)=\"choose('${RED}')\"></div>\n    <button (click)=\"reset()\">Reset</button>\n  </div>\n</div>\n"

        /***/
    }),

    /***/ 473: /***/ (function (module, exports) {

        module.exports = "<div class=\"well\">\n  <div class=\"color-previewer\" [ngStyle]=\"{color:color}\">\n    Preview Text Color\n  </div>\n</div>\n"

        /***/
    }),

    /***/ 474: /***/ (function (module, exports) {

        module.exports = "<div class=\"well\">\n  <p [ngStyle]=\"{'background':color}\">color-sample works!</p>\n</div>\n"

        /***/
    }),

    /***/ 475: /***/ (function (module, exports) {

        module.exports = "<p>\n  hero-table works!\n</p>\n"

        /***/
    }),

    /***/ 476: /***/ (function (module, exports) {

        module.exports = "<div>\n  site-header works!\n</div>\n"

        /***/
    }),

    /***/ 477: /***/ (function (module, exports) {

        module.exports = "<div>\n  Task Search:\n  <div class=\"btn-group\">\n    <input placeholder=\"{{placeholder}}\" #input/>\n    <button class=\"btn btn-find\" (click)=\"find(input)\">Find</button>\n    <button class=\"btn btn-clear\" (click)=\"clear(input)\">Clear</button>\n  </div>\n</div>\n"

        /***/
    }),

    /***/ 490: /***/ (function (module, exports, __webpack_require__) {

        module.exports = __webpack_require__(291);


        /***/
    })

}, [490]);
//# sourceMappingURL=main.bundle.js.map