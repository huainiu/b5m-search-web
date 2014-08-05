;
(function($, window, document) {

    $(function() {
        //有奖调查
        searchFed.indexFun();

        MiniFilter.init();

        setTimeout(function() {
            $('.side-l').b5m_fixTop({
                placeWrap: false,
                className: 'fixed',
                offsetBottom: 200
            });
        }, 1000);

        //左侧展开收起
        $('#J_category_nav').b5m_cateNav();

        //过滤属性选择
        $('#J_filter').b5m_toggleItem();

        //反馈弹出窗
        $('.popup-trigger').b5m_popup();
        $('.feed-awards').b5m_popup({
            fixed: true,
            cName: 'arrow-right'
        });

        $(window).resize(function() {
            var flag = null;
            clearTimeout(flag);

            flag = setTimeout(function() {
                searchFed.setPos();
            }, 400);

            var $box = $('#J_filter_cate'),
                $trigger = $box.find('.show-more');
            MiniFilter.winResize($trigger);
        });
        //弹出框调用
        $('.goods-list').find('.grid-ls').prodDetails({});
    });


    var searchFed = window.searchFed || {};

    searchFed = {
        indexFun: function() {
            this.insertFeed();
            // this.scrollBar();
            this.feedCheck();
        },
        insertFeed: function() {
            $('.gotop').before('<div class="feed-awards" data-target="feed-popup"></div>');
            this.setPos();
        },
        //调整返回顶部按钮位置
        setPos: function() {
            var posLeft = parseInt($('.wp').width() / 2 + 55);
            $('.gotop,.gotofushi,.feed-awards').css({
                'marginLeft': posLeft
            });
        },
        scrollBar: function() {
            setTimeout(function() {
                var hisList = $('.scroll-history .grid-view').find('li'),
                    len = hisList.length;
                if (len > 2) {
                    $('.scroll-history').css('height', 400);
                    // searchFed.scrollBar();
                    seajs.use(['$', 'modules/widgets/jscrollpane/2.0.19/jscrollpane'], function($, Jscroll) {
                        jscroll = new Jscroll({
                            container: $('.scroll-history')
                        });
                    });
                }
            }, 1000);
        },
        feedCheck: function() {
            $('.feed-form').find('.required').keyup(function(event) {
                if (typeof _basePath !== 'undefined') {
                    validate.check('feed-form', _basePath + "searchRecommend.htm");
                }
            });
        }
    };


    /**
     * 小屏幕下面顶部分类展开、收起
     */
    var MiniFilter = {
        init: function() {
            var $box = $('#J_filter_cate'),
                $trigger = $box.find('.show-more');

            this.bindEvent.apply($trigger, arguments);
            this.winResize($trigger);
        },
        bindEvent: function() {

            //这里的this已经是$trigger jquery对象
            var $this = this;
            $this.on('click.showmore', function() {
                var $this = $(this);
                $this.toggleClass(classOpen).parent().find('ul,dl').toggleClass('filter-open');

                if ($this.hasClass('open')) {
                    $this.html('<span>收起</span>');
                } else {
                    $this.html('<span>展开</span>');
                }
            });
        },
        //展开和收起在初次加载和window resize时显隐切换
        winResize: function(obj) {
            obj.each(function() {
                var $this = $(this),
                    $filtersBox = $this.parent().find('ul,dl'),
                    box_width = $filtersBox.width(),
                    $filters = $filtersBox.find('dd,li'),
                    filters_width = $filters.length * $filters.outerWidth(true);

                if (filters_width > box_width) {
                    $filtersBox
                        .parent()
                        .find('.show-more').show();
                } else {
                    $filtersBox
                        .parent()
                        .find('.show-more').hide();
                }
            });
        }
    }

    /*帮豆加分提示e*/
    var validate = {
        check: function(element, url) {
            var $target = $('.' + element),
                $items = $target.find('.required'),
                $submit_btn = $('#J_submit');

            $items.each(function() {
                var $this = $(this),
                    value = $.trim($this.val());

                if (!value) {
                    $submit_btn.addClass('btn-disable').removeClass('btn-enable');
                    return false;
                }
                $submit_btn.addClass('btn-enable').removeClass('btn-disable');
            });

            $submit_btn.off().on('click', function(e) {
                e.preventDefault();
                validate.submit($target, url);
            })
        },
        submit: function(obj, url) {
            var data = obj.serialize();
            url = url + "?" + data;

            $.ajax({
                url: url,
                data: {},
                success: function(data) {
                    var codes = data['code'];
                    if (Cookies.get('login') != "true") {
                        $('.popup-in').html('<div class="tips"><h5>您的意见已提交成功!</h5><p>谢谢参与，感谢您长久对帮5买的支持~</p></div>');
                    } else {
                        if (codes == 1) {
                            $.fn.b5m_addTips('6');
                        };
                    }
                }
            })
                .always(function() {
                    var flag = setTimeout(function() {
                        $('.feed-popup').hide();
                        clearTimeout(flag);
                    }, 4000);
                })
        }
    }

    /*================jquery插件开始处======================*/
    $.b5m = $.b5m || {};

    /**
     * 展开收起更多属性值
     */
    $.b5m.toggleItem = function(el, options) {
        var base = this;
        base.$el = $(el);
        base.el = el;

        $(window).resize(function() {
            clearTimeout(flag);
            var flag = setTimeout(function() {
                base.winResize();
            }, 600);
        });

        base.init = function() {
            base.options = $.extend({}, $.b5m.toggleItem.defaultOptions, options);
            $box = base.$el.find('.' + base.options.box);
            classFilter = base.options.classFilter;
            classMultiple = base.options.classMultiple;
            classOpen = base.options.classOpen;
            num = base.options.num;

            base.bindEvent();
            base.bindMore();
            base.winResize();
        }

        //绑定事件
        base.bindEvent = function() {

            //绑定展开收起事件
            $box.find('.show-more').on('click.showmore', function() {
                var $this = $(this);
                $parent = $this.parent();

                $this.toggleClass(classOpen)
                    .parent().toggleClass(classFilter);

                if ($this.hasClass('open')) {
                    $this.html('<span>收起</span>');
                } else {

                    //收起时，去掉多选
                    $this
                        .parent().removeClass(classMultiple);
                    $this.html('<span>展开</span>');
                }
            });

            //绑定多选事件
            $box.find('.btn-multiple').on('click', function(e) {
                $(this)
                    .parents('.filter-item').addClass(classMultiple + ' ' + classFilter);
                $(this).parent('.filter-act').siblings('.show-more').addClass("open").text("收起");
                e.preventDefault();
            });

            //取消多选事件
            $box.find('.btn-cancle').on('click', function(e) {
                var $this = $(this);
                $this
                    .parents('.' + base.options.box).removeClass(classMultiple)
                    .find('.show-more').trigger('click.showmore');
                e.preventDefault();
            });

            //确定多选事件
            $box.find('.btn-sure').on('click', function(e) {
                var $this = $(this),
                    $parent = $this.parents('.filter-item'),
                    $filter = $parent.find('.filter-lists').find('.cur'),
                    len = $filter.length,
                    title = $parent.find('dt').attr('title'),
                    data = '';

                $filter.each(function(index, el) {
                    if (index == len - 1) {
                        data += title + ':' + $(el).text();
                    } else {
                        data += title + ':' + $(el).text() + ',';
                    }
                });

                //提交数据
                searchMulti(data);
                e.preventDefault();

            });

            //每个条件添加事件
            $box.find('.filter-lists a').not('.not-filter').on('click', function(e) {
                var $this = $(this);
                $this.toggleClass('cur');

                //多选情况下，阻止a的默认事件
                if ($this.parents('.filter-item').hasClass('filter-multiply')) {
                    e.preventDefault();
                }
            });
        }

        /**
         * 展开收起其余属性值
         */
        base.bindMore = function() {
            var $this = $('#J_more'),
                $toggbleBar = $this.parent('.filter-more'),
                items_len = $box.length;

            //筛选条件小于5个则删除展开更多按钮
            var lenFlag = items_len < 5;

            if (lenFlag) {
                $toggbleBar.remove();
            }

            //标识最后一个是否为 ‘价格’
            var flag = $box.last().find('dt').text().slice(0, 2) == '价格';

            $this.on('click', function(e) {

                if (flag) {
                    $box.filter(function(index) {
                        return (index > (num - 2)) && index != (items_len - 1);
                    }).toggle();
                } else {
                    $box.filter(function(index) {
                        return (index > (num - 2));
                    }).toggle();
                }

                $(this).toggleClass('open');

                //展开和收起在初次加载和window resize时显隐切换
                base.winResize();
                e.preventDefault();
            });
        },

        //展开和收起在初次加载和window resize时显隐切换
        base.winResize = function() {
            $box.each(function() {
                var $this = $(this),
                    $filtersBox = $this.find('.filter-lists'),
                    box_width = $filtersBox.width(),
                    $filters = $filtersBox.find('a'),
                    filters_width = $filters.length * $filters.outerWidth(true);

                if (filters_width > box_width) {
                    $this.find('.show-more').show();
                } else {
                    $this.find('.show-more').hide();
                }
            });
        }
    };

    $.b5m.toggleItem.defaultOptions = {
        box: 'filter-item',
        classFilter: 'filter-open',
        classMultiple: 'filter-multiply',
        classOpen: 'open',
        num: 5
    };

    $.fn.b5m_toggleItem = function(options) {
        return this.each(function() {
            new $.b5m.toggleItem(this, options).init();
        });
    }

    /**
     * 置顶/置底效果
     */
    $.b5m.fixTop = function(el, options) {

        var base = this;

        base.el = el;
        base.$el = $(el);

        base.init = function() {
            options = $.extend({}, $.b5m.fixTop.Defaults, options || {});
            base.placeWrap = options.placeWrap;
            base.className = options.className;
            base.offsetBottom = options.offsetBottom;
            base.bindEvent();
        }

        /**
         * 获得元素的信息：宽、高和距离屏幕左、上的距离
         * @returns {{size: {width: *, height: *}, position: {left: *, top: *}}}
         */
        base.getEleInfo = function() {
            return {
                size: {
                    width: base.$el.width(),
                    height: base.$el.outerHeight(true)
                },
                position: {
                    left: base.$el.offset().left,
                    top: base.$el.offset().top
                }
            };
        }

        /**
         * 判断元素是否在可视区域内
         * @returns {boolean}
         */
        base.isInWin = function() {
            var isShowTop = (base.getEleInfo().position.top + base.getEleInfo().size.height) > $(window).scrollTop(),
                isShowBottom = base.getEleInfo().position.top < ($(window).scrollTop() + $(window).height());
            return isShowTop && isShowBottom;
        }

        base.bindEvent = function() {
            var $win = $(window),
                winH = $(window).height(),
                defaultPos = base.$el.css('position'),
                defaultTop = base.getEleInfo().position.top;
            zIndex = base.$el.css('zIndex');

            $win.scroll(function() {
                var scrollTop = $(window).scrollTop(),
                    elemH = base.$el.outerHeight();

                //当元素的高度大于浏览器高度
                if (elemH > winH) {

                    if ((defaultTop + elemH) <= (scrollTop + winH)) {

                        base.$el.css({
                            position: 'fixed',
                            bottom: 0,
                            zIndex: 70,
                            width: base.getEleInfo().size.width
                        }).addClass(base.className);

                        //当footer进入页面时，左侧位置不在吸底
                        if ($('.footer').offset().top <= scrollTop + winH) {
                            base.$el.css({
                                position: 'fixed',
                                bottom: scrollTop + winH - $('.footer').offset().top,
                                top: 'auto',
                                zIndex: 70,
                                width: base.getEleInfo().size.width
                            })
                        }

                    } else {

                        base.$el.css({
                            position: defaultPos,
                            bottom: 'auto',
                            top: 'auto',
                            zIndex: zIndex
                        }).removeClass(base.className);

                    }

                } else {
                    if (defaultTop <= scrollTop) {
                        base.$el.css({
                            position: 'fixed',
                            top: 0,
                            zIndex: 70,
                            width: base.getEleInfo().size.width
                        }).addClass(base.className);

                        //判断父类是否有占位标签
                        if (!base.$el.next('div').hasClass('placeWrap') && base.placeWrap) {
                            base.$el.after('<div class="placeWrap" style="height:' + base.getEleInfo().size.height + 'px"></div>');
                        }
                    } else {
                        base.$el.css({
                            position: defaultPos,
                            top: 'auto',
                            bottom: 'auto',
                            zIndex: zIndex,
                            width: base.getEleInfo().size.width
                        }).removeClass(base.className);

                        if (base.$el.next('div').hasClass('placeWrap') && base.placeWrap) {
                            base.$el.next('.placeWrap').remove();
                        }

                    }

                }
            })
        }
    }

    $.b5m.fixTop.Defaults = {
        placeWrap: true,
        className: "",
        offsetBottom: 0
    }

    $.fn.b5m_fixTop = function(options) {
        return this.each(function() {
            new $.b5m.fixTop(this, options).init();
        })
    }

    $.b5m.cateNav = function(el, options) {
        var base = this;
        base.el = el;
        base.$el = $(el);

        base.init = function() {
            base.options = $.extend({}, $.b5m.cateNav.Defaults, options || {}),
            base.className = base.options.className;
            base.nodeElement = base.options.nodeElement;
            base.showMore = base.options.showMore;
            base.moreOpen = base.options.moreOpen;
            base.num = base.options.num;

            base.bindEvent();
        }

        base.bindEvent = function() {

            var $items = base.$el.find(base.nodeElement),
                $itemLinks = $items.find('a'),
                itemLen = $items.length;


            //展开点击元素，收起其余兄弟节点。
            $items.on('click', function() {
                var _this = $(this);

                //当此元素展开时，再次点击就收起。
                _this.parent().toggleClass(base.className)
                    .siblings().removeClass(base.className);
                $(window).trigger('scroll');
            });

            //阻止冒泡
            $itemLinks.on('click', function(e) {
                e.stopPropagation();
            });

            if (itemLen <= base.num) {
                base.$el.find('.' + base.showMore).hide();
                return false;
            }

            var $targetItem = $items.parent().filter(function(index) {
                return index > (base.num - 1);
            });

            base.$el.find('.' + base.showMore).on('click', function() {
                var _this = $(this);

                if (_this.hasClass(base.moreOpen)) {
                    _this.removeClass(base.moreOpen);
                    $targetItem.hide().removeClass(base.className);
                } else {
                    _this.addClass(base.moreOpen);
                    $targetItem.show().removeClass(base.className);
                }

                //显隐历史记录、用户还点击了模块
                $('.goods-history').toggle();
            });
        }
    }

    $.b5m.cateNav.Defaults = {
        className: 'cur',
        nodeElement: 'dl dt',
        showMore: 'more-category',
        moreOpen: 'more-category-open',
        num: 5
    }

    $.fn.b5m_cateNav = function(options) {
        return this.each(function() {
            new $.b5m.cateNav(this, options).init();
        });
    }

    /**
     * 弹窗
     * @param el
     * @param options
     */
    $.b5m.popup = function(el, options) {
        var base = this;
        base.el = el,
        base.$el = $(el);

        base.init = function() {
            var opts = $.extend({}, $.b5m.popup.defaultOptions, options || {});
            var $target = $('.' + base.$el.data('target')).length ? $('.' + base.$el.data('target')) : '';

            base.$target = $target || $('.' + opts.target);
            base.$close = $('.' + opts.close);
            base.$arrow = $('.' + opts.arrow);
            base.fixed = opts.fixed;
            base.cName = opts.cName;

            this.bindEvent();
        };

        base.bindEvent = function() {

            base.$el.on('click', function() {

                //                if(base.$target.is(':hidden')){
                base.show();

                base.$target.on('click', function(e) {
                    e.stopPropagation();
                });

                $(document).on('click', function() {
                    base.hide();
                });

                base.$close.on('click', function() {
                    base.hide();
                })

                $(window).resize(function() {
                    var flag = null;
                    clearTimeout(flag);

                    setTimeout(function() {
                        base.setPos();
                    }, 1000);
                });
                //                }else{
                //                    base.hide();
                //                }
                return false;
            });
        };

        base.getPos = function() {
            var coord = null,
                x = parseInt(base.$el.offset().left),
                y = parseInt(base.$el.offset().top);

            return coord = {
                x: x,
                y: y
            };
        };

        base.setPos = function() {
            var coord = base.getPos();

            base.$target.css({
                'position': 'absolute',
                'left': coord.x - parseInt(base.$target.width() - base.$el.width()),
                'top': coord.y + 40,
                'bottom': 'auto'
            })

            if (base.fixed) {
                base.$target.css({
                    'position': 'fixed',
                    'left': coord.x - parseInt(base.$target.width()) - 30,
                    'top': 'auto',
                    'bottom': parseInt(base.$el.css('bottom'))
                })
            }
        };

        base.show = function() {
            base.$target.css({
                'display': 'block'
            });

            base.$arrow.removeClass().addClass('arrow ' + base.cName);

            base.setPos();
        };

        base.hide = function() {
            base.$target.css({
                'display': 'none'
            });
        };
    }

    $.b5m.popup.defaultOptions = {
        target: 'feed-popup',
        close: 'close',
        fixed: false,
        arrow: 'arrow',
        cName: 'arrow-top'
    };

    $.fn.b5m_popup = function(options) {
        return this.each(function() {
            new $.b5m.popup(this, options).init();
        });
    };

    /*帮豆加分提示s*/
    $.fn.b5m_bangdouTips = function() {
        var tips;

        function init(num) {
            return $('<div class="bangdou-tips" style="color:red"><div class="bangdou-tips__in">' + "+" + num + '</div></div>').appendTo($('.feed-popup').find('.btn-box'));
        }
        return {
            getTips: function(num) {
                return tips || (tips = init(num));
            }
        }
    }();

    $.fn.b5m_addTips = function(num) {
        $.fn.b5m_bangdouTips.getTips(num).stop(true, true).animate({
            opacity: 1
        }, 400).delay(500).animate({
            opacity: 0
        }, 800, function() {
            $('.popup-in').html('<div class="tips"><h5>您的意见已提交成功!</h5><p>谢谢参与，感谢您长久对帮5买的支持~</p></div>');
        });
    };

    /**
     * 弹出框内图片滚动
     */
    $.b5m.miniSlider = function(el, options) {
        var base = this;
        base.$el = $(el);
        base.el = el;

        base.init = function() {
            var opts = $.extend({}, $.b5m.miniSlider.defaultOptions, options || {});
            base.trigger = opts.trigger;

            base.bindEvent();
        }

        base.bindEvent = function() {
            var $container = base.$el,
                $slider = $container.find('ul'),
                $slider_item = $slider.find('li'),
                $slider_a = $slider.find('a'),
                $img = $slider_item.find('img'),
                item_w = $slider_item.outerWidth(true),
                step = $slider_item.length - 5,
                n = 0,
                $trigger = $container.find('.' + base.trigger),
                $targetImg = $container.siblings('.main-slider-pic').find('img'),

                $slider = $container.find('ul>li').eq(0);

            $img.on('mouseover', function(e) {

                var src = this.src;

                $targetImg[0].src = src;
                $slider_a.removeClass('active');
                $(this).parent('a').addClass('active');
                e.preventDefault();
            });

            //若缩略图小于4个就隐藏左右箭头
            if (step <= 0) {
                $trigger.hide();
                return false;
            }

            $trigger.eq(0).addClass('arrow-left-disable');

            $trigger.on('click', function(e) {
                if ($(this).hasClass('slider-right') && (n < step)) {
                    n++;
                    $slider.animate({
                        marginLeft: -item_w * n
                    });
                } else if ($(this).hasClass('slider-left') && (n >= 1)) {
                    n--;
                    $slider.animate({
                        marginLeft: -item_w * n
                    });
                }

                if (n > 0 && n < step) {
                    $trigger.removeClass('arrow-left-disable arrow-right-disable');
                } else if (n == 0) {
                    $trigger.eq(0).addClass('arrow-left-disable');
                    $trigger.eq(1).removeClass('arrow-right-disable')
                } else if (n == step) {
                    $trigger.eq(1).addClass('arrow-right-disable');
                    $trigger.eq(0).removeClass('arrow-left-disable')
                }
                e.preventDefault();
            });
        }
    }

    $.b5m.miniSlider.defaultOptions = {
        trigger: 'arrow'
    }

    $.fn.b5m_miniSlider = function(options) {
        return this.each(function() {
            new $.b5m.miniSlider(this, options).init();
        })
    }

    // $('.mini-slider').b5m_miniSlider({
    //     trigger: 'slider-trigger'
    // });

    /*================jquery插件结束处======================*/
})(jQuery, window, document);


$.fn.prodDetails = function(options) {
    return this.each(function() {
        new ProdDetail(this, options).init();
    });
}
/**
 * 商品的详细信息构造函数
 * @param {[type]} elem    [description]
 * @param {[type]} options [description]
 */

function ProdDetail(elem, options) {
    this.elem = elem;
    this.$elem = $(elem);
    this.$parent = this.$elem.parent();
    this.docId = this.$elem.attr('docId');
    this.iscompare = this.$elem.attr('iscompare');
    this.id = this.$elem.attr('id');
    this.posLeft = this.$elem.data('posLeft');
    this.offetLeft = this.$elem.offset().left;
    this.offsetWidth = this.$elem.outerWidth();

    this.options = options;

    this.defaults = {
        baseUrl: this.getBaseUrl(),
        url: this.getSearchPath() + '/goodsDetail/detailInfo.htm',
        adSize: 5,
        closeTrigger: 'pop-close'
    }
}

/**
 * [getBaseUrl 根据当前的路径返回相应的地址]
 * s.b5m.com -> 'http://s.b5m.com/'
 * search.bang5mail.com -> 'http://search.stage.bang5mai.com/'
 * @return {[string]} [当前的location.origin]
 */
ProdDetail.prototype.getBaseUrl = function() {
    return getBaseUrl();
    // return 'http://s.b5m.com/'
}

ProdDetail.prototype.getCol = function(){
	var location = window.location;
	if(!location.port || location.port == 80){
		var host = location.hostname;
		if(host.indexOf('haiwai') >= 0 || host.indexOf('usa') >= 0 || host.indexOf('korea') >= 0) return 'haiwaip';
		if(host.indexOf('jp') >= 0) return 'japan';
		return '';
	}
	if(location.href.indexOf('haiwai/s') >= 0 || location.href.indexOf('usa/s') >= 0 || location.href.indexOf('korea/s') >= 0) return 'haiwaip';
	if(location.href.indexOf('jp/s') >= 0) return 'japan';
	return '';
}

function getBaseUrl(){
	var location = window.location;
    if (location.origin) {
        return location.origin + '/';
    } else if (location.protocol && location.hostname) {
        return location.protocol + '//' + location.hostname + '/';
    }
}

ProdDetail.prototype.init = function() {
    this.config = $.extend({}, this.defaults, this.options);
    this.url = this.config.url;
    this.adSize = this.config.adSize;
    this.close = this.config.closeTrigger;
    this.docIds = this.config.docIds;
    this.baseUrl = this.config.baseUrl;
    //绑定事件
    this.bindEvent();
}
function initShare(content, pic, url) {
	seajs.use('modules/common/share/1.0.0/share.js', function(Shared) {
		new Shared({
			id : ".b5m-share",
			title : "帮5买全网兑换",
			content : content,
			pic : pic,
			href : url
		});
	});
}
ProdDetail.prototype.bindEvent = function() {
    var base = this,
        timeFlag = '',
        $target = base.$elem,
        $tags = $target.find('.grid-tags');
    // $modBox = $target.find('.grid-mod').data('moveTop',$tags.outerHeight(true));
    // $pop = $('.pop-ls');

    // $target.hover(function() {
    //     var $tail = $(this).find('.grid-tail');
    //     $tail.addClass('grid-tail-hover');
    // }, function() { 
    //     var $tail = $(this).find('.grid-tail');
    //     $tail.removeClass('grid-tail-hover');
    // });

    // 点击展开产品详情
    $target.find('.grid-tail').on('click', function() {

        var $this = $(this).parents('.grid-ls'),
            flag = $this.data('flag'),
            isOpen = $this.data('isOpen'),
            //点击产品的下标
            prodIndex = $('.grid-ls').index($this) + 1,
            //插入的位置
            posIndex = Math.ceil(prodIndex / 4);

        // $(this).addClass('grid-tail-active').delay(200).show(1, function() {
        // $(this).removeClass('grid-tail-hover grid-tail-active');
        // });
        $this.addClass('grid-ls-on').siblings('.grid-ls').data('flag', '').data('isOpen', '').removeClass('grid-ls-on');
        //flag标识是否为第一次请求，空为第一次，
        if (!flag) {
        	$('.pop-ls').remove();
            $this.data('flag', 'true');
            $this.data('isOpen', 'true');
            base.getDetail(posIndex, $this);
        } else {
            if (isOpen == 'true') {
                base.hidePop(base.docId, $this);
            } else {
                //显示对应的弹出框（根据docId来判断对应关系）
            	$('.pop-ls').remove();
                $this.data('flag', 'true');
                $this.data('isOpen', 'true');
                base.getDetail(posIndex, $this);
            }
        }
    });

    $target.find('.J_type a').on('click', function(e) {
        e.preventDefault();
        $(this).addClass('a-active').siblings('a').removeClass('a-active')
        $target.find('.J_price strong').eq($(this).index()).css('display', 'inline').siblings('strong').css('display', 'none');
    })
}
/**
 * 获取产品的详细信息
 * @param  {[Number]} index [弹出框需要插入第几行]
 */
ProdDetail.prototype.getSearchPath = function(){
	var server = location.hostname;
	if(server.indexOf("stage") > 0) return "http://search.stage.bang5mai.com";
	if(server.indexOf("prod") > 0) return "http://search.prod.bang5mai.com";
	return "http://s.b5m.com";
};
ProdDetail.prototype.getDetail = function(index, $this) {
    var base = this,
        url = base.url,
        iscompare = base.iscompare,
        docId = base.docId,
        $parent = base.$parent,
        adSize = base.adSize,
        index = index,
        compareBaseUrl = base.baseUrl + 'compare/',
        BaseUrl = base.baseUrl + 'item/',
        txtLen = 56;
    
    var req = $.ajax({
        url: url,
        dataType: 'jsonp',
        data: {
            "docId": docId,
            "isCompare": iscompare,
            "col" : base.getCol(),
        },
        jsonp: 'jsonCallback'
    });

    req.done(function(data) {
        var context = data.val,
            //绘制图表需要的ID
            price_ChartID = context.DetailDocId || context.DocId,
            //产品名称
            prodTitle = context.Title,
            //预测价格需要现在的价格
            forecastPrice = context.Price;
            context.searchPath = base.getSearchPath();
            context.daigouSource.HighPrice = context.daigouSource.HighPrice + "";
            if( context.daigouSource.HighPrice && context.daigouSource.HighPrice.indexOf('-') > 0 ){
            	context.daigouSource.HighPrice = context.daigouSource.HighPrice.split('-')[1];
            };
            if(!context.daigouSource.HighPrice || Number(context.daigouSource.HighPrice) < Number(context.daigouSource.Price)){
            	context.daigouSource.HighPrice = context.daigouSource.Price;
            }
            if(context.daigouSource.Price > 5){
            	context.daigouSource.Price = Number(context.daigouSource.Price) - 0.5;
            }
            context.remainPrice = Number(Number(context.daigouSource.HighPrice) - Number(context.daigouSource.Price)).toFixed(2);
            context.bangzhuan = parseInt(Number(context.daigouSource.Price) + 0.99);
            context.duihuanbangzhuan = context.bangzhuan * 100;
            if(!context.SalesAmount){
            	if(!context.SalesAmount) context.SalesAmount = parseInt(Math.random() * 490 + 10);
            }
        //Handlebarsjs模板解析
        var source = $("#entry-template").html(),
            template = Handlebars.compile(source),
            html = template(context);

        //handlebarsjs返回的html有多余空格导致 $(html)报错，所以去掉空格。
        var $html = $($.trim(html));

        var $targetElem = $parent.find('.grid-ls').eq(4 * index - 1);

        //指定位置插入弹出框
        if ($targetElem.length <= 0) {
            $html.appendTo($parent).css({
                display: 'block',
                height:'auto'
            }).stop(true, true);
        } else {
            $html.insertAfter($targetElem).css({
                display: 'block',
                height:'auto'
            }).stop(true, true);
        }

        //推荐商品
        base.getAds(prodTitle, '', '', adSize, false, base.docId);
        //初始化分享 content, pic, url
        initShare(context.daigouSource.Title, [context.daigouSource.Picture], getDetailUrl(context.daigouSource.DOCID));

        //添加价格趋势
        base.showProductPriceTrendByDocid(price_ChartID, data.val.Source, forecastPrice)
        base.setPos(docId);

        //显隐价格趋势图
        $html.find('.J_price_trend').hover(function() {
            base.showChart(price_ChartID);
        }, function() {
            base.hideChart(price_ChartID);
        });

        //隐藏产品详情弹出框
        $('#' + docId + '_prod').find('.pop-close').on('click', function() {
            var id = $(this).data('closeid');
            base.hidePop(id, $('#' + id));
        });

        // 缩略图切换效果
        $('.mini-slider').b5m_miniSlider({
            trigger: 'slider-trigger'
        });
        var cartCenter = new CartCenter({
    		docId: context.daigouSource.DOCID,
    		url:context.daigouSource.originUrl,
    		priceAvg:context.daigouSource.HighPrice,
    		searchPath:context.searchPath
    	});
    	cartCenter.init();
    	//降价提醒
    	//PriceCompare.addProd();
        //代购弹出框
        // $html.find(".bodyopen").click(function() {
        //     Daigou.show(data, $(this));
        // });
    });
}

ProdDetail.prototype.getAds = function(keywords, cookId, recordUrl, adSize, needShowAd, Id) {
    var base = this,
        docId = base.docId,
        html = '';
    // $.ajax({
    //     url: 'http://click.simba.taobao.b5m.com/s/data/' + adSize + '_0_V.html',
    //     type: 'GET',
    //     data: {
    //         keywords: keywords,
    //         // keywords : '春装',
    //         cid: cookId,
    //         isDetail: true
    //     },
    //     dataType: "jsonp",
    //     jsonp: 'jsoncallback',
    //     success: function(data) {
    //         var ads = data.val,
    //             ads_len = ads.length,
    //             //需要请求的推荐商品的数据量
    //             diff_val = adSize - ads_len,
    //             html = '';

    //         if (ads_len > 0) {
    //             var len = Math.min(ads_len, adSize);

    //             for (var i = 0; i < len; i++) {
    //                 var jumpUrl = ads[i].Url;
    //                 var picUrl = ads[i].Picture;
    //                 var title = ads[i].Title;
    //                 var price = ads[i].Price;
    //                 html += '<li><a target="_blank"  href="' + jumpUrl + '"><img src="' + picUrl + '"></a><span>&yen;' + price + '</span></li>';
    //             }

    //         } else if (diff_val > 0) {
    //   
    
    
    /*var relGoodsUrl = base.baseUrl;
    if(location.port && location.port != 80){
    	var link = location.href;
    	var firstIndex = link.indexOf("/", link.indexOf(location.hostname)) + 1;
    	var channel = link.substring(firstIndex, link.indexOf("/", firstIndex));
    	relGoodsUrl = relGoodsUrl + channel + "/";
    }
    $.ajax({
        url: relGoodsUrl + 's/relGoods.htm?t=' + new Date().getTime(),
        type: 'POST',
        data: {
            docId: docId,
            title: keywords,
            pageSize: 5
        },
        success: function(data) {
            var length = 5;
            for (var j = 0; j < length; j++) {
                if (data[j]) {
                    var item = data[j];
                    html += '<li><a target="_blank" href="' + getDetailUrl(item.DOCID) + '"><img src="' + item.Picture + '"></a><span>&yen;' + item.Price + '</span></li>';
                }
            }
            $('#' + Id + '_prod').find('.rec-prod-list').append(html);
        }
    })
    // }
    $('#' + Id + '_prod').find('.rec-prod-list').append(html);*/
    var req = $.ajax({
    	url:base.getSearchPath() + "/goodsDetail/more/data.htm",
    	dataType: 'jsonp',
    	data: {"docId": docId},
        jsonp: 'jsonCallback'
    });
    var have = false;
    req.done(function(data) {
    	var sources = data.val;
    	var $container = $("#recommand-source");
    	if(sources) {
    		for(var index = 0; index < sources.length; index++){
    			var source = sources[index];
    			$container.append('<li><a target="blank" href="' + getDetailUrl(source.DOCID) + '"><img src="'+source.Picture+'" alt=""><span class="fv-icon ' + base.getLogoIcon(source.Source) + '"></span><span class="icon-mask"></span><span class="mask-text">&yen;'+source.Price+'</span></a></li>');
    			have = true;
    		}
    	}
    	$(".pl-ctn .tit").text('全网比价');
    	if(have) setSliderWidth($(".pop-ls")[0]);
    	if(!have){
    		$.ajax({
    			url: 'http://click.simba.taobao.b5m.com/s/data/10_0_V.html',
    			type: 'GET',
    			data: {
    				keywords : keywords,
    				cid: cookId,
    				isDetail: true
    			},
    			dataType: "jsonp",
    			jsonp: 'jsoncallback',
    			success: function(data) {
    				var ads = data.val;
    				if(ads.length < 1) {
    					$container.hide();
    					return;
    				}
    				for (var i = 0; i < ads.length; i++) {
    					$container.append('<li><a target="blank" href="' + ads[i].Url + '"><img src="'+ads[i].Picture+'" alt=""><span class="' + base.getLogoIcon(ads[i].Source) + '"></span><span class="icon-mask"></span><span class="mask-text">&yen;'+ads[i].Price+'</span></a></li>');
    				}
    				$(".pl-ctn .tit").text('正品推荐');
    				setSliderWidth($(".pop-ls")[0]);
    			}
    		});
    	}
    });
    // }
    // });
}
ProdDetail.prototype.getLogoIcon = function(source){
	if('天猫' == source) return 'fv-icon icon-tianmao';
	if('京东商城' == source) return 'fv-icon icon-jingdong';
	if('卓越亚马逊' == source) return 'fv-icon icon-yamaxun';
	if('当当网' == source) return 'fv-icon icon-dangdang';
	if('1号店官网' == source) return 'fv-icon icon-1haodian';
	if('易迅网' == source) return 'fv-icon icon-yixun';
	if('唯品会官网' == source) return 'fv-icon icon-weipinhui';
	return "";
}
ProdDetail.prototype.showPop = function(id, obj) {
    $('#' + id + '_prod').css({
        display: 'block'
    }).animate({
        height: '397'
    }, 300, 'linear');
    obj.data('isOpen', 'true').addClass('grid-ls-on').siblings('.grid-ls').removeClass('grid-ls-on');
}

ProdDetail.prototype.hidePop = function(id, obj) {
    $('#' + id + '_prod').animate({
        height: 0
    }, 300, 'linear', function() {
        $(this).hide();
    });
    obj.data('isOpen', 'false').removeClass('grid-ls-on');
}

ProdDetail.prototype.setPos = function(id) {
    var base = this,
        popLeft = $('#' + id + '_prod').offset().left;

    $('#' + id + '_prod').find('.pop-arrow').css({
        left: (base.offetLeft - popLeft) + parseInt(base.offsetWidth / 2) - 9
    })
}

ProdDetail.prototype.showChart = function(chartId) {
    var $chart = $('#chart_' + chartId);
    $chart.fadeIn();
}

ProdDetail.prototype.hideChart = function(chartId) {
    var $chart = $('#chart_' + chartId);
    $chart.fadeOut();
}

/**
 * 绘制价格趋势
 * @param  {[string]} docid  [产品id]
 * @param  {[string]} source [来源商家]
 */
ProdDetail.prototype.showProductPriceTrendByDocid = function(docid, source, price) {
    var options = {
        height: "120",
        width: "275",
        titleAlign: "left",
        crosshairsColor: ["#ff1919", "#ff1919"],
        legendEnabled: false,
        handler: function(result) {
            return result.val.averiage;
        }
    };
    //图标初始化位置
    var $topPriceHistroyDiv = $("#chart_" + docid),
        url = this.getSearchPath() + "/pricehistory/goodsDetail.htm?fill=true&source=" + source + "&price=" + price;
    // url = 'http://search.stage.bang5mai.com/' + "/pricehistory/goodsDetail.htm?fill=true&source=" + source + "&price=" + price + '&docId=' + docid;

    $topPriceHistroyDiv.b5mtrend(docid, url, options);

    /*价格预测*/
    // $.ajax({
    //     url: url,
    //     type: 'POST',
    //     dataType: 'json'
    // })
    // .done(function(data) {
    //     var type = Number(data.val.forecastTrend) || 0,
    //         html = '',
    //         cName = '';

    //     if(type === -1){
    //         cName = 'price-down';
    //     }else if(type === 0){
    //         cName = 'price-flat';
    //     }else if(type === 1){
    //         cName = 'price-up';
    //     }

    //     html += '<div class="prod-price-hd">';
    //     html +=   '<span class="cl-eb7e">未来</span>价格预测：<i class="prod-question"><span class="prod-detail-info"><strong>价格预测：</strong>帮5买结合商品 的历史价格等因素预测的该 商品下次貂整后的价格 <em></em></span></i><em class="'+ cName +'">平稳</em> <strong class="cl-0B72C r">降价提醒<i class="prod-question"><span class="prod-detail-info"><strong>降价提醒：</strong>帮5买提供给用 户的专业化服务，当商品降 价时第一时间通知您 <em></em></span></i></strong>';
    //     html += '</div>';
    //     html += '<div class="prod-price-bd">';
    //     html += '<span>建议您：</span>';
    //     if(type === 1){
    //         html += '<p class="prod-suggest prod-by-hold">继续观望</p>';
    //     }else{
    //         html += '<p class="prod-suggest prod-by-b5m cl-eb7e">立即用“帮我买”<i class="prod-question"></i>购买</p>';
    //     }
    //     html += '</div>';

    //     $('.J_forecastPrice').html(html);
    // })
}

Handlebars.registerHelper('listMiniPicFun', function(sPic) {
    if (!sPic) {
        return false;
    }

    var aPic = sPic.split(','),
        htmls = '';

    for (var i = 0; i < aPic.length; i++) {
        htmls += '<li><span><img src="' + aPic[i] + '" alt=""></span></li>';
    }

    return htmls;
});

Handlebars.registerHelper('priceTrendTypeShowFun', function(type) {
    if(type == -1){
    	return '<span class="price-trend icon-down"><i class="trend"></i>近期下降<i class="down-circle"></i></span>';
    }
    if(type == 0){
    	return '<span class="price-trend icon-flat"><i class="trend"></i>近期平稳<i class="down-circle"></i></span>';
    }
   	return '<span class="price-trend icon-up"><i class="trend"></i>近期上升<i class="down-circle"></i></span>';
});

Handlebars.registerHelper('listProdServer', function(sInfo) {
    if (!sInfo) {
        return false;
    }

    var aInfo = sInfo.split('&'),
        htmls = '';

    for (var i = 0; i < aInfo.length; i++) {
        htmls += '<p><span class="prod-carriage"><span class="cl-5555 fs14">' + aInfo[i] + '</span></span></p>';
    }
    return htmls;
});

Handlebars.registerHelper('showUrl', function(docId, isCompare) {
    return getDetailUrl(docId);
});

function getDetailUrl(docId){
	if (!docId) {
        return ''
    }
	var baseUrl = getBaseUrl();
    /*var collection = "";
    var host = 's.b5m.com';
    if(location.host.indexOf("haiwai") >= 0){
    	collection = "haiwaip";
    }
    if(location.host.indexOf("korea") >= 0){
    	collection = "korea";
    }
    if(location.host.indexOf("usa") >= 0){
    	collection = "usa";
    }
    if(location.host.indexOf("jp") >= 0){
    	collection = "jp";
    }
    if(location.port && location.port != 80){
    	var link = location.href;
    	if(link.indexOf("?") > 0){
    		link = link.substring(0, link.indexOf("?"));
    	}
    	if(link.indexOf("haiwai") >= 0){
    		collection = "haiwaip";
    	}
    	if(link.indexOf("korea") >= 0){
    		collection = "korea";
    	}
    	if(link.indexOf("usa") >= 0){
    		collection = "usa";
    	}
    	if(link.indexOf("jp") >= 0){
    		collection = "jp";
    	}
    }
    var url = 'http://search.stage.bang5mai.com/item/' + collection + "/" + docId + '.html';*/
    return baseUrl + 'item/' + docId + '.html';
}

Handlebars.registerHelper('isShowArrow', function(sPic, options) {
    var aPic = sPic.split(','),
        len = aPic.length;

    if (len > 5) {
        return options.fn(this);
    } else {
        return options.inverse(this);
    }
})

Handlebars.registerHelper('showPriceTrend', function(isLowPrice, trend) {
    var price_trend = '';

    /**
     * isLowPrice  1:历史最低价，0：非历史最低价
     * trend -1:价格下降 0:价格平稳 1:价格上升
     */
    if (isLowPrice == '1') {
        price_trend = 'price-lowest';
    } else if (trend == '-1') {
        price_trend = 'price-down';
    } else if (trend == '0') {
        price_trend = 'price-flat';
    } else if (trend == '1') {
        price_trend = 'price-up';
    }

    return price_trend;
})

Handlebars.registerHelper('listSubDocs', function(subDocs) {
    var len = subDocs.length,
        htmls = '',
        mallPrice = '',
        mallUrl = '',
        mallName = '';

    //剔除第一条数据，所以从第二条开始循环
    for (var i = 1; i < len; i++) {

        mallPrice = subDocs[i].Price;
        mallUrl = subDocs[i].Url;
        mallName = subDocs[i].Source

        if (subDocs[i].Source == '淘宝网') {
            mallName = '淘宝网<span class="taobaoPerson">个人卖家</span>';
        }
        htmls += '<p><span class="pro-mall-item"><span class="cl-5555 fs14">&yen;' + mallPrice + '</span><a target="_blank" data-attr="100804" href="' + mallUrl + '" class="ml20 cl-eb7e">' + mallName + '</a></span></p>'
    }
    return htmls;
})


Handlebars.registerHelper('listTags', function(tags) {
    var len = tags.length,
        htmls = '';

    if (len < 0) {
        return false;
    }
    len = Math.min(len, 5);
    htmls = '<dl class="comment-list l"><dt>大家都觉得：</dt><dd>';

    for (var i = 0; i < len; i++) {
        htmls += '<a href="javascript:void();">' + tags[i] + '</a>';
    }

    htmls += '</dd></dl>';

    return htmls;
});
Handlebars.registerHelper('baseUrl', function() {
    var location = window.location;

    if (location.origin) {
        return location.origin;
    } else if (location.protocol && location.hostname) {
        return location.protocol + '//' + location.hostname;
    }
})
// /*价格趋势预测*/
// Handlebars.registerHelper('forecastPrice',function(type){
//     var type = Number(type) || 0,
//         cName = '';

//     if(type === -1){
//         cName = 'price-down';
//     }else if(type === 0){
//         cName = 'price-flat';
//     }else if(type === 1){
//         cName = 'price-up';
//     }

//     return cName;
// });