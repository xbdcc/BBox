package com.carlos.bbox.zhihu;

import com.carlos.bbox.zhihu.bean.ZhihuDailyBeforeVO;
import com.carlos.bbox.zhihu.bean.ZhihuDailyTodayVO;
import com.carlos.bbox.zhihu.bean.ZhihuDailyDetailVO;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by caochang on 2017/6/27.
 */

public interface ZhihuApi {

    String HOST = "http://news-at.zhihu.com/api/4/";

    /**
     * 最新日报
     * https://news-at.zhihu.com/api/4/news/latest
     * {"date":"20170713","stories":[{"title":"谁说只有走「性冷淡」风的建筑看起来才高级？","ga_prefix":"071311","images":["https:\/\/pic3.zhimg.com\/v2-492c1f9d550f7e367839ac5221654096.jpg"],"multipic":true,"type":0,"id":9520758},{"images":["https:\/\/pic4.zhimg.com\/v2-bf95d345848aa5ae07e0d1d172b16573.jpg"],"type":0,"id":9522012,"ga_prefix":"071310","title":"看谁更能赚钱，并不是单纯比较毛利率那么简单"},{"images":["https:\/\/pic2.zhimg.com\/v2-739bd35f6b71bf51a281ba6a8409bed9.jpg"],"type":0,"id":9521662,"ga_prefix":"071309","title":"多亏了保护，这些濒危野生动物的数量都在逐渐恢复"},{"images":["https:\/\/pic2.zhimg.com\/v2-92f3a4143a3877f603261b9dd7f69eb9.jpg"],"type":0,"id":9520846,"ga_prefix":"071308","title":"三星这回可能要赚翻了，没办法，连苹果也要给它好多钱"},{"images":["https:\/\/pic2.zhimg.com\/v2-5e692c8902ca83ff6c9e4248673369b5.jpg"],"type":0,"id":9521657,"ga_prefix":"071307","title":"时间晶体，把爱保存到宇宙尽头之后"},{"images":["https:\/\/pic4.zhimg.com\/v2-adcb005945ebb5409bba997d7e98c2ef.jpg"],"type":0,"id":9521356,"ga_prefix":"071307","title":"想应聘最火热的人工智能，先做这几道面试题吧"},{"images":["https:\/\/pic4.zhimg.com\/v2-a63058580cc58ebb5d9f919f987ff853.jpg"],"type":0,"id":9521360,"ga_prefix":"071307","title":"几年间，新加坡的 GDP 逐渐超过了香港"},{"images":["https:\/\/pic1.zhimg.com\/v2-125268c369db8f94f4409acc497c8c40.jpg"],"type":0,"id":9519829,"ga_prefix":"071306","title":"瞎扯 · 如何正确地吐槽"}],"top_stories":[{"image":"https:\/\/pic1.zhimg.com\/v2-ebb74c1962fe109c018278122acf3454.jpg","type":0,"id":9521360,"ga_prefix":"071307","title":"几年间，新加坡的 GDP 逐渐超过了香港"},{"image":"https:\/\/pic4.zhimg.com\/v2-1e6b23dfe1edaa60469102cca0a3ae23.jpg","type":0,"id":9521356,"ga_prefix":"071307","title":"想应聘最火热的人工智能，先做这几道面试题吧"},{"image":"https:\/\/pic2.zhimg.com\/v2-d30622e20035dc8e95c2657dbdfd31e9.jpg","type":0,"id":9520745,"ga_prefix":"071215","title":"有些建筑就像美人，时尚换了一轮又一轮它却惊艳千年"},{"image":"https:\/\/pic4.zhimg.com\/v2-0aa406fedda5d6973509267f9e96aa27.jpg","type":0,"id":9520658,"ga_prefix":"071217","title":"- 林清玄先生您觉得台湾什么最好吃？\r\n- 馒头"},{"image":"https:\/\/pic4.zhimg.com\/v2-b1a068e33a7f31918e37c497f3b9bc87.jpg","type":0,"id":9519781,"ga_prefix":"071207","title":"一颗国产卫星被遗落半路，前几天终于吭哧吭哧爬上了轨道"}]}
     * @return
     */
    @GET("news/latest")
    Flowable<ZhihuDailyTodayVO> getTodayDaily();

    /**
     * 往期日报
     *https://news-at.zhihu.com/api/4/news/before/20161221
     * {"date":"20161220","stories":[{"images":["http:\/\/pic4.zhimg.com\/0d901afb3639db05dc5e21ab27df6a23.jpg"],"type":0,"id":9082491,"ga_prefix":"122022","title":"小事 · 就这么失踪了"},{"images":["http:\/\/pic2.zhimg.com\/3aa829848d78065339992a97a25e4889.jpg"],"type":0,"id":9081356,"ga_prefix":"122021","title":"醒醒吧，一段经历改变一个人的桥段只会在电影里发生"},{"images":["http:\/\/pic4.zhimg.com\/366a75acd1f3683568e8326bb87ba6cb.jpg"],"type":0,"id":9081771,"ga_prefix":"122020","title":"做了银行管培生，是不是马上就可以走上人生巅峰了？"},{"images":["http:\/\/pic2.zhimg.com\/191028fa1c5964fb876f39a6e97e3f7d.jpg"],"type":0,"id":9069910,"ga_prefix":"122019","title":"为什么事情一多，我就什么都注意不过来了？"},{"images":["http:\/\/pic3.zhimg.com\/9252eaf04d3199929f9349ca153839b6.jpg"],"type":0,"id":9077606,"ga_prefix":"122018","title":"对，就是那个表现「突出」的椎间盘"},{"images":["http:\/\/pic4.zhimg.com\/bc39e96d85ce619e7b3ab03e38ffacdb.jpg"],"type":0,"id":9081991,"ga_prefix":"122017","title":"知乎好问题 · 怎样找到靠谱的心理咨询师？"},{"images":["http:\/\/pic4.zhimg.com\/065eaf62100407e134adbdd344db8707.jpg"],"type":0,"id":9074746,"ga_prefix":"122016","title":"有点心疼「霸道总裁」，他们真的更有可能得「神经病」"},{"images":["http:\/\/pic3.zhimg.com\/19420700fdf749139f3f034b52ba326a.jpg"],"type":0,"id":9081484,"ga_prefix":"122015","title":"如何评估雾霾对人身健康的影响？"},{"images":["http:\/\/pic4.zhimg.com\/017df1ad07e9cdf10f56be8d8ce26db3.jpg"],"type":0,"id":9077703,"ga_prefix":"122014","title":"一层 30cm 和三层 10cm 的装甲，哪种更结实？"},{"images":["http:\/\/pic4.zhimg.com\/395c3bd5a99645d4e4be6860a24e821f.jpg"],"type":0,"id":9079993,"ga_prefix":"122013","title":"重要的东西，务必要抓在自己手里"},{"images":["http:\/\/pic1.zhimg.com\/51a8730f75dc20411d4cd8ba0b786e08.jpg"],"type":0,"id":9080806,"ga_prefix":"122012","title":"大误 · 如何带歪一个高冷的艺术生？"},{"images":["http:\/\/pic3.zhimg.com\/91bce4299a0abf30866261bb8dab09d2.jpg"],"type":0,"id":9076921,"ga_prefix":"122011","title":"为什么中国动画的配音总是那么「假」？"},{"images":["http:\/\/pic2.zhimg.com\/7903266a53d37695a95290f0f5f25c29.jpg"],"type":0,"id":9078609,"ga_prefix":"122010","title":"不给高额彩礼，还能证明我是个好女婿吗？"},{"images":["http:\/\/pic2.zhimg.com\/70fc05efbb05d0b128c2b70e927fb8e9.jpg"],"type":0,"id":9079901,"ga_prefix":"122009","title":"看，地铁窗外的广告动起来了"},{"images":["http:\/\/pic4.zhimg.com\/446a4b49e90d99d68ddd532a2ebe9ec7.jpg"],"type":0,"id":9076032,"ga_prefix":"122008","title":"听起来很厉害的「全基因组关联分析」，能算命吗？"},{"images":["http:\/\/pic1.zhimg.com\/2f5b094b83571468088a91ab5c089528.jpg"],"type":0,"id":9076190,"ga_prefix":"122007","title":"「反正都被骂过了，就再说一次春运火车票要不要涨价」"},{"images":["http:\/\/pic3.zhimg.com\/858d99668e0e9e08915fe12af5d42d4e.jpg"],"type":0,"id":9079829,"ga_prefix":"122007","title":"在线支付越来越普遍，但用纸币的时候，你是「匿名」的"},{"images":["http:\/\/pic2.zhimg.com\/fa7239c0f4d612c629dc4101064e0d79.jpg"],"type":0,"id":9079953,"ga_prefix":"122007","title":"2016 年度盘点 · 中国 + 美国 = 全球 x 70%"},{"images":["http:\/\/pic2.zhimg.com\/89169830957472cc4ca72fd92430cbc1.jpg"],"type":0,"id":9079807,"ga_prefix":"122007","title":"读读日报 24 小时热门 TOP 5 · 张艺谋批评史"},{"images":["http:\/\/pic2.zhimg.com\/da0ff0c6c902bc8fde81f3a9af09ec09.jpg"],"type":0,"id":9078719,"ga_prefix":"122006","title":"瞎扯 · 如何正确地吐槽"},{"images":["http:\/\/pic1.zhimg.com\/8909445c27500719aa7448fbbe0d2544.jpg"],"type":0,"id":9078915,"ga_prefix":"122006","title":"这里是广告 · XX 到此一游，是曾经最时髦的旅游签到打卡"}]}
     * @param date
     * @return
     */
    @GET("news/before/{date}")
    Flowable<ZhihuDailyBeforeVO> getBeforeDaily(@Path("date") String date);

    /**
     * 日报详情
     * https://news-at.zhihu.com/api/4/news/9530275
     * {"body":"<div class=\"main-wrap content-wrap\">\n<div class=\"headline\">\n\n<div class=\"img-place-holder\"><\/div>\n\n\n\n<\/div>\n\n<div class=\"content-inner\">\n\n\n\n\n<div class=\"question\">\n<h2 class=\"question-title\"><\/h2>\n\n<div class=\"answer\">\n\n<div class=\"content\">\n<p>回答来自机构帐号：<a class=\"avatar-link\" href=\"https:\/\/www.zhihu.com\/org\/lonelyplanet-15\" target=\"_blank\"><span class=\"name\">LonelyPlanet<\/span><\/a><\/p>\n<\/div>\n<\/div>\n\n\n<\/div>\n\n\n\n\n\n<div class=\"question\">\n<h2 class=\"question-title\">有哪些已经或正在消失的景点？<\/h2>\n\n<div class=\"answer\">\n\n<div class=\"meta\">\n<img class=\"avatar\" src=\"http:\/\/pic2.zhimg.com\/v2-67a134a37074a181a191a4e858a88991_is.jpg\">\n<span class=\"author\">LonelyPlanet，<\/span><span class=\"bio\">世界上最大最专业的旅行内容提供者、顶级旅行指南出版商<\/span>\n<\/div>\n\n<div class=\"content\">\n<p>自从马耳他的&ldquo;蓝窗&rdquo;塌了之后，许多人都忧心忡忡地打开自己的旅行计划小本本，把一些景点赶紧提上日程，生怕永远错过一个世界级风景。很多读者在我们微信（LonelyPlanet-CN）后台留言，关键字都是&ldquo;即将消失的景点&rdquo;&hellip;&hellip;<\/p>\r\n<p>你可能听说过大堡礁离濒临消失，威尼斯水城也将成为过去式......既然受到了邀请，Lonely Planet 为你开个清单，列出一些不为人知而又即将要消失的好风景~抓紧去看吧！<\/p>\r\n<p><strong>0 1 | 埃及 曼纳城 <\/strong><\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic1.zhimg.com\/70\/v2-5342d78f3c8c6f26a6bc77bb29a5bab4_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>photo by Institute for the Study of the Ancient World via Flickr<\/em> 近年来，由于地下水位的上升，城市人口增多和过度的农业占地，曼纳城的古迹正在被破坏。曼纳城得名于圣曼纳。圣曼纳是一名属于罗马军团的埃及士兵，因为拒不放弃自己的基督教信仰而殉道。4 世纪末，圣曼纳之墓周边的地区成为神迹的代名词。到了 5 世纪，最远来自欧洲的各地朝圣者们蜂拥而至，该地区竟形成了一个被称为&ldquo;殉难者城&rdquo;（Martyroupolis）的繁华城市&mdash;作为朝圣地点，仅比耶路撒冷略逊一筹。<\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic1.zhimg.com\/70\/v2-fe3d13aef3ccb4d2ef1a5bbc950f7840_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>photo by Institute for the Study of the Ancient World via Flickr<\/em> 由于贝都因人的多次袭击和盗窃，曾经十分宏伟的拜占庭时期殉难者城中心所剩无几，但巨大的教堂轮廓尚在，而且相当清晰。教堂的圣坛上方盖了一个朴素的木屋小礼拜堂，圣坛石板仍位于原处。圣曼纳的部分遗体保存在小礼拜堂的一个柜子里。从小礼拜堂开始，一排残存的短粗柱子通往圣人的墓室。地下水位的变化导致考古挖掘现场受损，也使得该考古地点名列联合国教科文组织濒危古迹名录。<\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic1.zhimg.com\/70\/v2-7f9a60b86893ab0bd1e1554c8aafebec_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>photo by Institute for the Study of the Ancient World via Flickr<\/em><\/p>\r\n<p><em><img class=\"content-image\" src=\"http:\/\/pic2.zhimg.com\/70\/v2-fc67411517be92c29959128551886cad_b.jpg\" alt=\"\" \/><\/em><\/p>\r\n<p><em>photo by Institute for the Study of the Ancient World via Flickr<\/em> 虽然古迹受到了损坏，但对埃及科普特基督徒的历史感兴趣的游客仍能在曼纳城（位于今 Burg al-Arab）感受到这里在埃及基督教早期所起的重要作用。曼纳城和圣曼纳修道院南侧之间有一条沙漠小路相连（普通机动车可以通行）。如果你不知道该往哪个方向走，就在修道院打听一下。<\/p>\r\n<p><strong>0 2 | 美国 大沼泽地国家公园 <\/strong><\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic4.zhimg.com\/70\/v2-ab301eb8008e21e1b46719274884db7b_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>&copy;Lonely Planet<\/em><\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic4.zhimg.com\/70\/v2-19e6fc907ed131efef01afd16baa1fe3_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>&copy;Lonely Planet<\/em> 佛罗里达州南部常常会与美女联系在一起，但它最吸引人的地方远不只是躺在白沙滩上休息的模特。该地区真正迷人的莫过于缓缓流经锯齿草原的涓涓细流，其蜿蜒流入短吻鳄和水獭的聚居地（那里有许多在泥滩中穿梭的蛇），再流经长满莎草的盆地，注入蓝绿色的佛罗里达湾（Florida Bay）。这就是大沼泽地，一片一望无际的旷野。<\/p>\r\n<p><em><img class=\"content-image\" src=\"http:\/\/pic1.zhimg.com\/70\/v2-8a129653e81be4937b765fb4d1db794c_b.jpg\" alt=\"\" \/><\/em><\/p>\r\n<p><em>&copy;Lonely Planet<\/em> 对大沼泽地的复杂环境最准确的描述是，它是一年中大部分时间都被淹没的大草原。这里生机盎然，雨季，宽阔的河流在克拉莎草下缓缓流动，流过茂盛的柏树林和硬木丛，流向大海。<\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic4.zhimg.com\/70\/v2-a264efeafb6b8407f2c5eed6270aa38f_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>&copy;Lonely Planet<\/em> 这里的景色让人感觉缓慢而永恒，因此相比于乘坐吵闹且不停震动的风力船探索沼泽地，步行、骑自行车、划独木舟或皮划艇（或露营）更让人感到心旷神怡。在这片独特的亚热带荒地上，生活着许多让人难以置信的奇妙生物，如短吻鳄、宽吻海豚、海牛、雪鹭、苍鹭、美洲蛇鹈、白头海雕和鱼鹰。<\/p>\r\n<p><strong>0 3 | 乌干达 卡苏比王陵 <\/strong><\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic3.zhimg.com\/70\/v2-255f38aa9d2ba1c4a6529e64c48f8cd2_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>photo by Nao lizuka via Flickr<\/em><\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic1.zhimg.com\/70\/v2-8080a4fedff7a442829c96ccd7c3f54c_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>photo by Andrew Moore via Flickr<\/em> 作为对布干达王国有重要意义的文化遗迹，卡苏比王陵被列入联合国教科文组织《世界遗产名录》，王陵中埋葬着布干达王国的最后 4 个国王及其皇室成员。这个茅草屋顶的大型宫殿建筑始建于 1882 年，最初是当作穆特萨一世国王（Kabaka Mutesa I）的王宫，但两年后国王去世，这里就变成了他的陵墓。<\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic3.zhimg.com\/70\/v2-9ff3516e40677bae42aa0115f4e605f2_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>photo by Rachel Strohm via Flickr<\/em><\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic3.zhimg.com\/70\/v2-a29f55cbd6503119fc8c9071c3d59e32_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>photo by Nao lizuka via Flickr<\/em> 结果，接下来的三个国王（kabaka）&mdash;穆旺加（Mwanga）、察瓦二世（Daudi Chawa II）以及现任国王罗纳德穆特比二世（Ronald Mutebi II）的父亲爱德华穆特萨二世（Edward Mutesa II）&mdash;都打破传统，选择埋葬在这里，而不是埋葬在自己的皇宫里。 可惜的是，2010 年 3 月，卡苏比王陵毁于一场意外火灾，不过王陵的修建工作正在进行，预计 2016 年将会全部完成。陵墓主体外面一圈的坟墓埋葬的是前国王遗孀的家人（幸运的是这些坟墓没有被烧毁）。皇室成员的长眠地在后面的树林中间，那里整体看来颇像一座小村庄。<\/p>\r\n<p><strong>0 4 | 菲律宾 伊富高水稻梯田 <\/strong><\/p>\r\n<p><em><img class=\"content-image\" src=\"http:\/\/pic1.zhimg.com\/70\/v2-3c5d44d3d286c1d0c6607ec2b8bb206c_b.jpg\" alt=\"\" \/><\/em><\/p>\r\n<p><em>&copy;Lonely Planet<\/em> 总共有 5 片伊富高水稻梯田被联合国教科文组织列入世界遗产名录，分别是巴塔德梯田、邦雅安（Bangaan）梯田、梅奥瑶梯田（Mayoyao）、洪端（Hungduan）梯田和基安甘（Kiangan）附近的纳加卡丹（Nagacadan）梯田。<\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic4.zhimg.com\/70\/v2-1a5f86956a7988ac65d445088db1325f_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>&copy;Lonely Planet<\/em><\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic4.zhimg.com\/70\/v2-6621e74c62a576de08bbed8e1e60c0f7_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>&copy;Lonely Planet<\/em> 伊富高人的水稻梯田在一年中的任何时间都美不胜收，不过最美的是收获前的 1 到 2 个月，此时的梯田是鲜绿色的，之后将逐渐转为金黄。而在种植时节，梯田呈现出毫无修饰的荒芜感，同样也很有魅力。 在巴纳韦，最佳观赏时间为 6 月到 7 月（收获之前）和 2 月到 3 月（清理和播种时节）；而在巴塔德，由于这里每年耕种两次，因此水稻田绿意最浓的时间是 4 月到 5 月以及 10 月到 11 月。如果你对人们为保护梯田而付出的努力感兴趣，或者想更了解这些梯田，可以研究一下拯救伊富高梯田运动（Save the Ifugao Terraces Movement，<a href=\"http:\/\/sitmo.org\">http:\/\/sitmo.org<\/a>）<\/p>\r\n<p><strong>0 5 |&nbsp;<\/strong><strong>科索沃 中世纪建筑群遗址<\/strong><\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic4.zhimg.com\/70\/v2-7a1cd1bbb8821c3aa2281b8c53147d2f_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>photo by Johannes Zielcke via Flickr<\/em> 科索沃几乎是冲突与破坏的同义词，但许多人都没有意识到，这里隐藏着大量拜占庭&mdash;罗马式教会建筑和一段中世纪的历史。四处凄凉但美丽的遗迹&mdash;13 世纪和 14 世纪的几所修道院 Dečani、Patriarchate of Peć和 Gračanica，还有列维萨圣女教堂（Church of the Virgin of Ljevi&scaron;a）&mdash;合在一起被称为&ldquo;中世纪遗址&rdquo;建筑群（Medieval Monuments）。<\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic4.zhimg.com\/70\/v2-cb1d3139b86e1f7311379e185448a253_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>photo by qiv via Flickr<\/em><\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic3.zhimg.com\/70\/v2-f54c42898dff69af45ff53faf31bf306_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>photo by Panoramas via Flickr<\/em> 由于政治动荡，这些用壁画装饰得富丽堂皇的遗址仍然受到严重威胁。相比之下，首都普里什蒂纳（Pri&scaron;tina）则对现代世界敞开了怀抱&mdash;在这个充满强烈自豪感与独立精神的自信国家，只需到那些繁忙的酒吧和咖啡馆转转即可。 <strong>0 6 |委内瑞拉 科罗<\/strong><\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic4.zhimg.com\/70\/v2-a3e6417291b8010af30d3b60e75da807_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>Photo by Karina Manghi via Flickr<\/em> 科罗（Coro）作为西班牙的殖民城市，历史可追溯到 16 世纪，是加勒比泥土建筑的典范。大约 602 座古建筑构成了这座城市的中心，其中大多数都是 18 世纪及 19 世纪的教堂和商人居住区。在这里也能看到荷兰对它的强烈影响。由于大雨的破坏和无所顾忌的开发计划，这里在 2005 年被列入联合国教科文组织濒危遗址名单。作为替代，旅行者可以考虑游览附近的 Los Medanos de Coro 国家公园，你能够在此探索高达 40 米的移动沙丘。<\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic1.zhimg.com\/70\/v2-e80cd154073709a77f6e2e0b1d4cbbe8_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>Photo by Sergio Gonzalez via Flickr<\/em><\/p>\r\n<p><strong>0 7 | 耶路撒冷 耶路撒冷老城 <\/strong><\/p>\r\n<p><em><img class=\"content-image\" src=\"http:\/\/pic2.zhimg.com\/70\/v2-d7737bc9c4668accf192c7aaa9757565_b.jpg\" alt=\"\" \/><\/em><\/p>\r\n<p><em>photo by RonAlmog via Flickr<\/em><\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic1.zhimg.com\/70\/v2-59b72a609eb4231db5dbb526dd11dec8_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>photo by Scott Ableman via Flickr<\/em> 只有少数城市拥有耶路撒冷这样复杂的人类历史，它对犹太人、基督徒和穆斯林都同样具有重要的象征意义。自从 1982 年以来，老城（Old City）的 220 处历史遗迹就已被列为濒危名单，其中包括哭墙、圣墓教堂（Church of the Holy Sepulchre）和 7 世纪的圆顶清真寺（Dome of the Rock）等标志性建筑。<\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic1.zhimg.com\/70\/v2-bb5aef80c93968186c389782de561200_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>photo by RonAlmog via Flickr<\/em><\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic1.zhimg.com\/70\/v2-082490c0879a35080170bd51abd0219c_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>photo by RonAlmog via Flickr<\/em> 持续的政治动荡、城市的无序扩张以及旅行者的泛滥一直威胁着对这些瑰宝的保护，因此请放轻脚步，并考虑去谢尔盖庭院（Sergei Courtyard）游览，在那里能找到绿色文化中心（Green Culture Centre）。中心由自然保护学会（Society for the Protection of Nature，简称 SPN）经营，组织一些对城市影响很小的团队游，从截然不同的角度展示耶路撒冷，将旅行者的影响降低到最低程度。<\/p>\r\n<p><strong>0 8 | 秘鲁 昌昌考古区 <\/strong><\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic3.zhimg.com\/70\/v2-7cd74e64a6dd656f71e37f0b5d9629c6_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>photo by Alexandre Milit&atilde;o via Flickr<\/em><\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic4.zhimg.com\/70\/v2-0ca6d991754aa5bb5f27f19fc8d1996f_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>photo by Chris Bryant - New Zealand via Flickr<\/em> 昌昌（Chan Chan）是秘鲁境内古老的奇穆王国（Chimu Kingdom）的首都，在哥伦布到达美洲之前，曾经是这片大陆上最大的人类定居区。这座土筑的奇异城市有一系列奢华的城郭，建于 9 世纪中叶。1470 年，在 3 万名居民落入印加人手中之前不久，此地已达到了极盛期。联合国教科文组织最初在 1986 年敲响警钟，但这个遗址仍然持续受到威胁&mdash;厄尔尼诺现象带来龙卷风和洪水，盗贼也在废墟上劫掠。<\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic2.zhimg.com\/70\/v2-796215fb7493819faf0a645d74cd7555_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>photo by Chris via Flickr<\/em><\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic4.zhimg.com\/70\/v2-a2d111aec27cb6b227d6ec42cc99e61f_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>photo by orientalizing via Flickr<\/em><\/p>\r\n<p><img class=\"content-image\" src=\"http:\/\/pic3.zhimg.com\/70\/v2-e66c562c44aa756927b81925f2da61c6_b.jpg\" alt=\"\" \/><\/p>\r\n<p><em>photo by Chris via Flickr<\/em><\/p>\r\n<p>因此，旅行者最好北上，到游人较少的查查波亚斯（Chachapoyas）地区参观那里的考古奇迹，包括 Karajia 令人称奇的石棺，以及古代贵族木乃伊。<\/p>\r\n<hr \/>\r\n<p>原文首发于微信公众号：LonelyPlanet-CN<\/p>\r\n<p>欢迎关注微信公众号：LonelyPlanet-CN<\/p>\n<\/div>\n<\/div>\n\n\n<div class=\"view-more\"><a href=\"http:\/\/www.zhihu.com\/question\/62412142\">查看知乎讨论<span class=\"js-question-holder\"><\/span><\/a><\/div>\n\n<\/div>\n\n\n\n\n\n<div class=\"question\">\n<h2 class=\"question-title\"><\/h2>\n\n<div class=\"answer\">\n\n<div class=\"content\">\n<p>「知乎机构帐号」是机构用户专用的知乎帐号，与知乎社区内原有的个人帐号独立并行，其使用者为有正规资质的组织机构，包括但不限于科研院所、公益组织、政府机关、媒体、企业等。这不仅是知乎对机构的「身份认证」，更是涵盖了内容流通机制、帐号规范等全套帐号体系。和个人帐号一样，机构帐号开通不需要任何费用，同时也受社区规范的监督管理，并要遵守相关协议。目前机构帐号入驻采用邀请制。您可以通过<span class=\"s1\">&nbsp;&nbsp;<a href=\"http:\/\/zhihu.com\/org-intro\"><span class=\"s2\">什么是「知乎机构帐号」<\/span><\/a>&nbsp;<\/span>来了解更多机构帐号信息。<\/p>\n<\/div>\n<\/div>\n\n\n<\/div>\n\n\n<\/div>\n<\/div>","image_source":"Lonely Planet \/ 知乎","title":"这些地方再不去，你可能再也没机会见了","image":"https:\/\/pic4.zhimg.com\/v2-6bb97a4df1e60aa2f9724d7d023608cb.jpg","share_url":"http:\/\/daily.zhihu.com\/story\/9530275","js":[],"ga_prefix":"072020","images":["https:\/\/pic2.zhimg.com\/v2-ca98244dae6d0f75c0da6f08ed760e45.jpg"],"type":0,"id":9530275,"css":["http:\/\/news-at.zhihu.com\/css\/news_qa.auto.css?v=4b3e3"]}
     */
    @GET("news/{id}")
    Flowable<ZhihuDailyDetailVO> getDetailDaily(@Path("id") String id);

//    @GET("/api/4/news/latest")
//    Flowable<ZhihuDailyTodayVO> getLastDaily();

//
//    /**
//     * 主题日报详情
//     */
//    @GET("theme/{id}")
//    Flowable<ThemeChildListBean> getThemeChildList(@Path("id") int id);
//
//    /**
//     * 专栏日报
//     */
//    @GET("sections")
//    Flowable<SectionListBean> getSectionList();
//
//    /**
//     * 专栏日报详情
//     */
//    @GET("section/{id}")
//    Flowable<SectionChildListBean> getSectionChildList(@Path("id") int id);
//
//    /**
//     * 热门日报
//     */
//    @GET("news/hot")
//    Flowable<HotListBean> getHotList();
//
//    /**
//     * 日报详情
//     */
//    @GET("news/{id}")
//    Flowable<ZhihuDetailBean> getDetailInfo(@Path("id") int id);
//
//    /**
//     * 日报的额外信息
//     */
//    @GET("story-extra/{id}")
//    Flowable<DetailExtraBean> getDetailExtraInfo(@Path("id") int id);
//
//    /**
//     * 日报的长评论
//     */
//    @GET("story/{id}/long-comments")
//    Flowable<CommentBean> getLongCommentInfo(@Path("id") int id);
//
//    /**
//     * 日报的短评论
//     */
//    @GET("story/{id}/short-comments")
//    Flowable<CommentBean> getShortCommentInfo(@Path("id") int id);



}
