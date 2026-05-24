--
-- PostgreSQL database dump
--

\restrict mxRna34d9GBuJXhdYvbdgMGnCWUAC4KjFFRj2lyCdT77tVuhW7TI0AFvPFVG7zd

-- Dumped from database version 17.10
-- Dumped by pg_dump version 18.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: sms_coupon; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sms_coupon (
    id bigint NOT NULL,
    coupon_type boolean,
    coupon_img character varying(2000),
    coupon_name character varying(100),
    num integer,
    amount numeric(18,4),
    per_limit integer,
    min_point numeric(18,4),
    start_time timestamp with time zone,
    end_time timestamp with time zone,
    use_type boolean,
    note character varying(200),
    publish_count integer,
    use_count integer,
    receive_count integer,
    enable_start_time timestamp with time zone,
    enable_end_time timestamp with time zone,
    code character varying(64),
    member_level boolean,
    publish boolean
);


ALTER TABLE public.sms_coupon OWNER TO faris;

--
-- Name: TABLE sms_coupon; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sms_coupon IS '优惠券信息';


--
-- Name: COLUMN sms_coupon.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon.id IS 'id';


--
-- Name: COLUMN sms_coupon.coupon_type; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon.coupon_type IS '优惠卷类型[0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券]';


--
-- Name: COLUMN sms_coupon.coupon_img; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon.coupon_img IS '优惠券图片';


--
-- Name: COLUMN sms_coupon.coupon_name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon.coupon_name IS '优惠卷名字';


--
-- Name: COLUMN sms_coupon.num; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon.num IS '数量';


--
-- Name: COLUMN sms_coupon.amount; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon.amount IS '金额';


--
-- Name: COLUMN sms_coupon.per_limit; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon.per_limit IS '每人限领张数';


--
-- Name: COLUMN sms_coupon.min_point; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon.min_point IS '使用门槛';


--
-- Name: COLUMN sms_coupon.start_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon.start_time IS '开始时间';


--
-- Name: COLUMN sms_coupon.end_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon.end_time IS '结束时间';


--
-- Name: COLUMN sms_coupon.use_type; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon.use_type IS '使用类型[0->全场通用；1->指定分类；2->指定商品]';


--
-- Name: COLUMN sms_coupon.note; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon.note IS '备注';


--
-- Name: COLUMN sms_coupon.publish_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon.publish_count IS '发行数量';


--
-- Name: COLUMN sms_coupon.use_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon.use_count IS '已使用数量';


--
-- Name: COLUMN sms_coupon.receive_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon.receive_count IS '领取数量';


--
-- Name: COLUMN sms_coupon.enable_start_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon.enable_start_time IS '可以领取的开始日期';


--
-- Name: COLUMN sms_coupon.enable_end_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon.enable_end_time IS '可以领取的结束日期';


--
-- Name: COLUMN sms_coupon.code; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon.code IS '优惠码';


--
-- Name: COLUMN sms_coupon.member_level; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon.member_level IS '可以领取的会员等级[0->不限等级，其他-对应等级]';


--
-- Name: COLUMN sms_coupon.publish; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon.publish IS '发布状态[0-未发布，1-已发布]';


--
-- Name: sms_coupon_history; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sms_coupon_history (
    id bigint NOT NULL,
    coupon_id bigint,
    member_id bigint,
    member_nick_name character varying(64),
    get_type boolean,
    create_time timestamp with time zone,
    use_type boolean,
    use_time timestamp with time zone,
    order_id bigint,
    order_sn bigint
);


ALTER TABLE public.sms_coupon_history OWNER TO faris;

--
-- Name: TABLE sms_coupon_history; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sms_coupon_history IS '优惠券领取历史记录';


--
-- Name: COLUMN sms_coupon_history.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon_history.id IS 'id';


--
-- Name: COLUMN sms_coupon_history.coupon_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon_history.coupon_id IS '优惠券id';


--
-- Name: COLUMN sms_coupon_history.member_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon_history.member_id IS '会员id';


--
-- Name: COLUMN sms_coupon_history.member_nick_name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon_history.member_nick_name IS '会员名字';


--
-- Name: COLUMN sms_coupon_history.get_type; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon_history.get_type IS '获取方式[0->后台赠送；1->主动领取]';


--
-- Name: COLUMN sms_coupon_history.create_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon_history.create_time IS '创建时间';


--
-- Name: COLUMN sms_coupon_history.use_type; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon_history.use_type IS '使用状态[0->未使用；1->已使用；2->已过期]';


--
-- Name: COLUMN sms_coupon_history.use_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon_history.use_time IS '使用时间';


--
-- Name: COLUMN sms_coupon_history.order_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon_history.order_id IS '订单id';


--
-- Name: COLUMN sms_coupon_history.order_sn; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon_history.order_sn IS '订单号';


--
-- Name: sms_coupon_history_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sms_coupon_history_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sms_coupon_history_id_seq OWNER TO faris;

--
-- Name: sms_coupon_history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sms_coupon_history_id_seq OWNED BY public.sms_coupon_history.id;


--
-- Name: sms_coupon_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sms_coupon_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sms_coupon_id_seq OWNER TO faris;

--
-- Name: sms_coupon_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sms_coupon_id_seq OWNED BY public.sms_coupon.id;


--
-- Name: sms_coupon_spu_category_relation; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sms_coupon_spu_category_relation (
    id bigint NOT NULL,
    coupon_id bigint,
    category_id bigint,
    category_name character varying(64)
);


ALTER TABLE public.sms_coupon_spu_category_relation OWNER TO faris;

--
-- Name: TABLE sms_coupon_spu_category_relation; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sms_coupon_spu_category_relation IS '优惠券分类关联';


--
-- Name: COLUMN sms_coupon_spu_category_relation.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon_spu_category_relation.id IS 'id';


--
-- Name: COLUMN sms_coupon_spu_category_relation.coupon_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon_spu_category_relation.coupon_id IS '优惠券id';


--
-- Name: COLUMN sms_coupon_spu_category_relation.category_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon_spu_category_relation.category_id IS '产品分类id';


--
-- Name: COLUMN sms_coupon_spu_category_relation.category_name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon_spu_category_relation.category_name IS '产品分类名称';


--
-- Name: sms_coupon_spu_category_relation_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sms_coupon_spu_category_relation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sms_coupon_spu_category_relation_id_seq OWNER TO faris;

--
-- Name: sms_coupon_spu_category_relation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sms_coupon_spu_category_relation_id_seq OWNED BY public.sms_coupon_spu_category_relation.id;


--
-- Name: sms_coupon_spu_relation; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sms_coupon_spu_relation (
    id bigint NOT NULL,
    coupon_id bigint,
    spu_id bigint,
    spu_name character varying(255)
);


ALTER TABLE public.sms_coupon_spu_relation OWNER TO faris;

--
-- Name: TABLE sms_coupon_spu_relation; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sms_coupon_spu_relation IS '优惠券与产品关联';


--
-- Name: COLUMN sms_coupon_spu_relation.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon_spu_relation.id IS 'id';


--
-- Name: COLUMN sms_coupon_spu_relation.coupon_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon_spu_relation.coupon_id IS '优惠券id';


--
-- Name: COLUMN sms_coupon_spu_relation.spu_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon_spu_relation.spu_id IS 'spu_id';


--
-- Name: COLUMN sms_coupon_spu_relation.spu_name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_coupon_spu_relation.spu_name IS 'spu_name';


--
-- Name: sms_coupon_spu_relation_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sms_coupon_spu_relation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sms_coupon_spu_relation_id_seq OWNER TO faris;

--
-- Name: sms_coupon_spu_relation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sms_coupon_spu_relation_id_seq OWNED BY public.sms_coupon_spu_relation.id;


--
-- Name: sms_home_adv; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sms_home_adv (
    id bigint NOT NULL,
    name character varying(100),
    pic character varying(500),
    start_time timestamp with time zone,
    end_time timestamp with time zone,
    status boolean,
    click_count integer,
    url character varying(500),
    note character varying(500),
    sort integer,
    publisher_id bigint,
    auth_id bigint
);


ALTER TABLE public.sms_home_adv OWNER TO faris;

--
-- Name: TABLE sms_home_adv; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sms_home_adv IS '首页轮播广告';


--
-- Name: COLUMN sms_home_adv.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_adv.id IS 'id';


--
-- Name: COLUMN sms_home_adv.name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_adv.name IS '名字';


--
-- Name: COLUMN sms_home_adv.pic; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_adv.pic IS '图片地址';


--
-- Name: COLUMN sms_home_adv.start_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_adv.start_time IS '开始时间';


--
-- Name: COLUMN sms_home_adv.end_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_adv.end_time IS '结束时间';


--
-- Name: COLUMN sms_home_adv.status; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_adv.status IS '状态';


--
-- Name: COLUMN sms_home_adv.click_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_adv.click_count IS '点击数';


--
-- Name: COLUMN sms_home_adv.url; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_adv.url IS '广告详情连接地址';


--
-- Name: COLUMN sms_home_adv.note; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_adv.note IS '备注';


--
-- Name: COLUMN sms_home_adv.sort; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_adv.sort IS '排序';


--
-- Name: COLUMN sms_home_adv.publisher_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_adv.publisher_id IS '发布者';


--
-- Name: COLUMN sms_home_adv.auth_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_adv.auth_id IS '审核者';


--
-- Name: sms_home_adv_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sms_home_adv_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sms_home_adv_id_seq OWNER TO faris;

--
-- Name: sms_home_adv_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sms_home_adv_id_seq OWNED BY public.sms_home_adv.id;


--
-- Name: sms_home_subject; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sms_home_subject (
    id bigint NOT NULL,
    name character varying(200),
    title character varying(255),
    sub_title character varying(255),
    status boolean,
    url character varying(500),
    sort integer,
    img character varying(500)
);


ALTER TABLE public.sms_home_subject OWNER TO faris;

--
-- Name: TABLE sms_home_subject; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sms_home_subject IS '首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】';


--
-- Name: COLUMN sms_home_subject.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_subject.id IS 'id';


--
-- Name: COLUMN sms_home_subject.name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_subject.name IS '专题名字';


--
-- Name: COLUMN sms_home_subject.title; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_subject.title IS '专题标题';


--
-- Name: COLUMN sms_home_subject.sub_title; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_subject.sub_title IS '专题副标题';


--
-- Name: COLUMN sms_home_subject.status; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_subject.status IS '显示状态';


--
-- Name: COLUMN sms_home_subject.url; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_subject.url IS '详情连接';


--
-- Name: COLUMN sms_home_subject.sort; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_subject.sort IS '排序';


--
-- Name: COLUMN sms_home_subject.img; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_subject.img IS '专题图片地址';


--
-- Name: sms_home_subject_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sms_home_subject_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sms_home_subject_id_seq OWNER TO faris;

--
-- Name: sms_home_subject_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sms_home_subject_id_seq OWNED BY public.sms_home_subject.id;


--
-- Name: sms_home_subject_spu; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sms_home_subject_spu (
    id bigint NOT NULL,
    name character varying(200),
    subject_id bigint,
    spu_id bigint,
    sort integer
);


ALTER TABLE public.sms_home_subject_spu OWNER TO faris;

--
-- Name: TABLE sms_home_subject_spu; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sms_home_subject_spu IS '专题商品';


--
-- Name: COLUMN sms_home_subject_spu.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_subject_spu.id IS 'id';


--
-- Name: COLUMN sms_home_subject_spu.name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_subject_spu.name IS '专题名字';


--
-- Name: COLUMN sms_home_subject_spu.subject_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_subject_spu.subject_id IS '专题id';


--
-- Name: COLUMN sms_home_subject_spu.spu_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_subject_spu.spu_id IS 'spu_id';


--
-- Name: COLUMN sms_home_subject_spu.sort; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_home_subject_spu.sort IS '排序';


--
-- Name: sms_home_subject_spu_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sms_home_subject_spu_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sms_home_subject_spu_id_seq OWNER TO faris;

--
-- Name: sms_home_subject_spu_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sms_home_subject_spu_id_seq OWNED BY public.sms_home_subject_spu.id;


--
-- Name: sms_member_price; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sms_member_price (
    id bigint NOT NULL,
    sku_id bigint,
    member_level_id bigint,
    member_level_name character varying(100),
    member_price numeric(18,4),
    add_other boolean
);


ALTER TABLE public.sms_member_price OWNER TO faris;

--
-- Name: TABLE sms_member_price; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sms_member_price IS '商品会员价格';


--
-- Name: COLUMN sms_member_price.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_member_price.id IS 'id';


--
-- Name: COLUMN sms_member_price.sku_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_member_price.sku_id IS 'sku_id';


--
-- Name: COLUMN sms_member_price.member_level_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_member_price.member_level_id IS '会员等级id';


--
-- Name: COLUMN sms_member_price.member_level_name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_member_price.member_level_name IS '会员等级名';


--
-- Name: COLUMN sms_member_price.member_price; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_member_price.member_price IS '会员对应价格';


--
-- Name: COLUMN sms_member_price.add_other; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_member_price.add_other IS '可否叠加其他优惠[0-不可叠加优惠，1-可叠加]';


--
-- Name: sms_member_price_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sms_member_price_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sms_member_price_id_seq OWNER TO faris;

--
-- Name: sms_member_price_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sms_member_price_id_seq OWNED BY public.sms_member_price.id;


--
-- Name: sms_seckill_promotion; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sms_seckill_promotion (
    id bigint NOT NULL,
    title character varying(255),
    start_time timestamp with time zone,
    end_time timestamp with time zone,
    status smallint,
    create_time timestamp with time zone,
    user_id bigint
);


ALTER TABLE public.sms_seckill_promotion OWNER TO faris;

--
-- Name: TABLE sms_seckill_promotion; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sms_seckill_promotion IS '秒杀活动';


--
-- Name: COLUMN sms_seckill_promotion.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_promotion.id IS 'id';


--
-- Name: COLUMN sms_seckill_promotion.title; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_promotion.title IS '活动标题';


--
-- Name: COLUMN sms_seckill_promotion.start_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_promotion.start_time IS '开始日期';


--
-- Name: COLUMN sms_seckill_promotion.end_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_promotion.end_time IS '结束日期';


--
-- Name: COLUMN sms_seckill_promotion.status; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_promotion.status IS '上下线状态';


--
-- Name: COLUMN sms_seckill_promotion.create_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_promotion.create_time IS '创建时间';


--
-- Name: COLUMN sms_seckill_promotion.user_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_promotion.user_id IS '创建人';


--
-- Name: sms_seckill_promotion_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sms_seckill_promotion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sms_seckill_promotion_id_seq OWNER TO faris;

--
-- Name: sms_seckill_promotion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sms_seckill_promotion_id_seq OWNED BY public.sms_seckill_promotion.id;


--
-- Name: sms_seckill_session; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sms_seckill_session (
    id bigint NOT NULL,
    name character varying(200),
    start_time timestamp with time zone,
    end_time timestamp with time zone,
    status boolean,
    create_time timestamp with time zone
);


ALTER TABLE public.sms_seckill_session OWNER TO faris;

--
-- Name: TABLE sms_seckill_session; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sms_seckill_session IS '秒杀活动场次';


--
-- Name: COLUMN sms_seckill_session.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_session.id IS 'id';


--
-- Name: COLUMN sms_seckill_session.name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_session.name IS '场次名称';


--
-- Name: COLUMN sms_seckill_session.start_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_session.start_time IS '每日开始时间';


--
-- Name: COLUMN sms_seckill_session.end_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_session.end_time IS '每日结束时间';


--
-- Name: COLUMN sms_seckill_session.status; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_session.status IS '启用状态';


--
-- Name: COLUMN sms_seckill_session.create_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_session.create_time IS '创建时间';


--
-- Name: sms_seckill_session_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sms_seckill_session_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sms_seckill_session_id_seq OWNER TO faris;

--
-- Name: sms_seckill_session_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sms_seckill_session_id_seq OWNED BY public.sms_seckill_session.id;


--
-- Name: sms_seckill_sku_notice; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sms_seckill_sku_notice (
    id bigint NOT NULL,
    member_id bigint,
    sku_id bigint,
    session_id bigint,
    subcribe_time timestamp with time zone,
    send_time timestamp with time zone,
    notice_type boolean
);


ALTER TABLE public.sms_seckill_sku_notice OWNER TO faris;

--
-- Name: TABLE sms_seckill_sku_notice; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sms_seckill_sku_notice IS '秒杀商品通知订阅';


--
-- Name: COLUMN sms_seckill_sku_notice.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_sku_notice.id IS 'id';


--
-- Name: COLUMN sms_seckill_sku_notice.member_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_sku_notice.member_id IS 'member_id';


--
-- Name: COLUMN sms_seckill_sku_notice.sku_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_sku_notice.sku_id IS 'sku_id';


--
-- Name: COLUMN sms_seckill_sku_notice.session_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_sku_notice.session_id IS '活动场次id';


--
-- Name: COLUMN sms_seckill_sku_notice.subcribe_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_sku_notice.subcribe_time IS '订阅时间';


--
-- Name: COLUMN sms_seckill_sku_notice.send_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_sku_notice.send_time IS '发送时间';


--
-- Name: COLUMN sms_seckill_sku_notice.notice_type; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_sku_notice.notice_type IS '通知方式[0-短信，1-邮件]';


--
-- Name: sms_seckill_sku_notice_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sms_seckill_sku_notice_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sms_seckill_sku_notice_id_seq OWNER TO faris;

--
-- Name: sms_seckill_sku_notice_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sms_seckill_sku_notice_id_seq OWNED BY public.sms_seckill_sku_notice.id;


--
-- Name: sms_seckill_sku_relation; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sms_seckill_sku_relation (
    id bigint NOT NULL,
    promotion_id bigint,
    promotion_session_id bigint,
    sku_id bigint,
    seckill_price numeric(10,4),
    seckill_count integer,
    seckill_limit integer,
    seckill_sort integer
);


ALTER TABLE public.sms_seckill_sku_relation OWNER TO faris;

--
-- Name: TABLE sms_seckill_sku_relation; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sms_seckill_sku_relation IS '秒杀活动商品关联';


--
-- Name: COLUMN sms_seckill_sku_relation.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_sku_relation.id IS 'id';


--
-- Name: COLUMN sms_seckill_sku_relation.promotion_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_sku_relation.promotion_id IS '活动id';


--
-- Name: COLUMN sms_seckill_sku_relation.promotion_session_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_sku_relation.promotion_session_id IS '活动场次id';


--
-- Name: COLUMN sms_seckill_sku_relation.sku_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_sku_relation.sku_id IS '商品id';


--
-- Name: COLUMN sms_seckill_sku_relation.seckill_price; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_sku_relation.seckill_price IS '秒杀价格';


--
-- Name: COLUMN sms_seckill_sku_relation.seckill_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_sku_relation.seckill_count IS '秒杀总量';


--
-- Name: COLUMN sms_seckill_sku_relation.seckill_limit; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_sku_relation.seckill_limit IS '每人限购数量';


--
-- Name: COLUMN sms_seckill_sku_relation.seckill_sort; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_seckill_sku_relation.seckill_sort IS '排序';


--
-- Name: sms_seckill_sku_relation_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sms_seckill_sku_relation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sms_seckill_sku_relation_id_seq OWNER TO faris;

--
-- Name: sms_seckill_sku_relation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sms_seckill_sku_relation_id_seq OWNED BY public.sms_seckill_sku_relation.id;


--
-- Name: sms_sku_full_reduction; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sms_sku_full_reduction (
    id bigint NOT NULL,
    sku_id bigint,
    full_price numeric(18,4),
    reduce_price numeric(18,4),
    add_other boolean
);


ALTER TABLE public.sms_sku_full_reduction OWNER TO faris;

--
-- Name: TABLE sms_sku_full_reduction; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sms_sku_full_reduction IS '商品满减信息';


--
-- Name: COLUMN sms_sku_full_reduction.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_sku_full_reduction.id IS 'id';


--
-- Name: COLUMN sms_sku_full_reduction.sku_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_sku_full_reduction.sku_id IS 'spu_id';


--
-- Name: COLUMN sms_sku_full_reduction.full_price; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_sku_full_reduction.full_price IS '满多少';


--
-- Name: COLUMN sms_sku_full_reduction.reduce_price; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_sku_full_reduction.reduce_price IS '减多少';


--
-- Name: COLUMN sms_sku_full_reduction.add_other; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_sku_full_reduction.add_other IS '是否参与其他优惠';


--
-- Name: sms_sku_full_reduction_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sms_sku_full_reduction_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sms_sku_full_reduction_id_seq OWNER TO faris;

--
-- Name: sms_sku_full_reduction_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sms_sku_full_reduction_id_seq OWNED BY public.sms_sku_full_reduction.id;


--
-- Name: sms_sku_ladder; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sms_sku_ladder (
    id bigint NOT NULL,
    sku_id bigint,
    full_count integer,
    discount numeric(4,2),
    price numeric(18,4),
    add_other boolean
);


ALTER TABLE public.sms_sku_ladder OWNER TO faris;

--
-- Name: TABLE sms_sku_ladder; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sms_sku_ladder IS '商品阶梯价格';


--
-- Name: COLUMN sms_sku_ladder.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_sku_ladder.id IS 'id';


--
-- Name: COLUMN sms_sku_ladder.sku_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_sku_ladder.sku_id IS 'spu_id';


--
-- Name: COLUMN sms_sku_ladder.full_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_sku_ladder.full_count IS '满几件';


--
-- Name: COLUMN sms_sku_ladder.discount; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_sku_ladder.discount IS '打几折';


--
-- Name: COLUMN sms_sku_ladder.price; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_sku_ladder.price IS '折后价';


--
-- Name: COLUMN sms_sku_ladder.add_other; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_sku_ladder.add_other IS '是否叠加其他优惠[0-不可叠加，1-可叠加]';


--
-- Name: sms_sku_ladder_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sms_sku_ladder_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sms_sku_ladder_id_seq OWNER TO faris;

--
-- Name: sms_sku_ladder_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sms_sku_ladder_id_seq OWNED BY public.sms_sku_ladder.id;


--
-- Name: sms_spu_bounds; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sms_spu_bounds (
    id bigint NOT NULL,
    spu_id bigint,
    grow_bounds numeric(18,4),
    buy_bounds numeric(18,4),
    work boolean
);


ALTER TABLE public.sms_spu_bounds OWNER TO faris;

--
-- Name: TABLE sms_spu_bounds; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sms_spu_bounds IS '商品spu积分设置';


--
-- Name: COLUMN sms_spu_bounds.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_spu_bounds.id IS 'id';


--
-- Name: COLUMN sms_spu_bounds.grow_bounds; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_spu_bounds.grow_bounds IS '成长积分';


--
-- Name: COLUMN sms_spu_bounds.buy_bounds; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_spu_bounds.buy_bounds IS '购物积分';


--
-- Name: COLUMN sms_spu_bounds.work; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sms_spu_bounds.work IS '优惠生效情况[1111（四个状态位，从右到左）;0 - 无优惠，成长积分是否赠送;1 - 无优惠，购物积分是否赠送;2 - 有优惠，成长积分是否赠送;3 - 有优惠，购物积分是否赠送【状态位0：不赠送，1：赠送】]';


--
-- Name: sms_spu_bounds_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sms_spu_bounds_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sms_spu_bounds_id_seq OWNER TO faris;

--
-- Name: sms_spu_bounds_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sms_spu_bounds_id_seq OWNED BY public.sms_spu_bounds.id;


--
-- Name: undo_log; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.undo_log (
    id bigint NOT NULL,
    branch_id bigint NOT NULL,
    xid character varying(100) NOT NULL,
    context character varying(128) NOT NULL,
    rollback_info bytea NOT NULL,
    log_status integer NOT NULL,
    log_created timestamp with time zone NOT NULL,
    log_modified timestamp with time zone NOT NULL,
    ext character varying(100)
);


ALTER TABLE public.undo_log OWNER TO faris;

--
-- Name: undo_log_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.undo_log_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.undo_log_id_seq OWNER TO faris;

--
-- Name: undo_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.undo_log_id_seq OWNED BY public.undo_log.id;


--
-- Name: sms_coupon id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_coupon ALTER COLUMN id SET DEFAULT nextval('public.sms_coupon_id_seq'::regclass);


--
-- Name: sms_coupon_history id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_coupon_history ALTER COLUMN id SET DEFAULT nextval('public.sms_coupon_history_id_seq'::regclass);


--
-- Name: sms_coupon_spu_category_relation id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_coupon_spu_category_relation ALTER COLUMN id SET DEFAULT nextval('public.sms_coupon_spu_category_relation_id_seq'::regclass);


--
-- Name: sms_coupon_spu_relation id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_coupon_spu_relation ALTER COLUMN id SET DEFAULT nextval('public.sms_coupon_spu_relation_id_seq'::regclass);


--
-- Name: sms_home_adv id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_home_adv ALTER COLUMN id SET DEFAULT nextval('public.sms_home_adv_id_seq'::regclass);


--
-- Name: sms_home_subject id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_home_subject ALTER COLUMN id SET DEFAULT nextval('public.sms_home_subject_id_seq'::regclass);


--
-- Name: sms_home_subject_spu id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_home_subject_spu ALTER COLUMN id SET DEFAULT nextval('public.sms_home_subject_spu_id_seq'::regclass);


--
-- Name: sms_member_price id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_member_price ALTER COLUMN id SET DEFAULT nextval('public.sms_member_price_id_seq'::regclass);


--
-- Name: sms_seckill_promotion id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_seckill_promotion ALTER COLUMN id SET DEFAULT nextval('public.sms_seckill_promotion_id_seq'::regclass);


--
-- Name: sms_seckill_session id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_seckill_session ALTER COLUMN id SET DEFAULT nextval('public.sms_seckill_session_id_seq'::regclass);


--
-- Name: sms_seckill_sku_notice id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_seckill_sku_notice ALTER COLUMN id SET DEFAULT nextval('public.sms_seckill_sku_notice_id_seq'::regclass);


--
-- Name: sms_seckill_sku_relation id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_seckill_sku_relation ALTER COLUMN id SET DEFAULT nextval('public.sms_seckill_sku_relation_id_seq'::regclass);


--
-- Name: sms_sku_full_reduction id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_sku_full_reduction ALTER COLUMN id SET DEFAULT nextval('public.sms_sku_full_reduction_id_seq'::regclass);


--
-- Name: sms_sku_ladder id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_sku_ladder ALTER COLUMN id SET DEFAULT nextval('public.sms_sku_ladder_id_seq'::regclass);


--
-- Name: sms_spu_bounds id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_spu_bounds ALTER COLUMN id SET DEFAULT nextval('public.sms_spu_bounds_id_seq'::regclass);


--
-- Name: undo_log id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.undo_log ALTER COLUMN id SET DEFAULT nextval('public.undo_log_id_seq'::regclass);


--
-- Data for Name: sms_coupon; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sms_coupon (id, coupon_type, coupon_img, coupon_name, num, amount, per_limit, min_point, start_time, end_time, use_type, note, publish_count, use_count, receive_count, enable_start_time, enable_end_time, code, member_level, publish) FROM stdin;
\.


--
-- Data for Name: sms_coupon_history; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sms_coupon_history (id, coupon_id, member_id, member_nick_name, get_type, create_time, use_type, use_time, order_id, order_sn) FROM stdin;
\.


--
-- Data for Name: sms_coupon_spu_category_relation; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sms_coupon_spu_category_relation (id, coupon_id, category_id, category_name) FROM stdin;
\.


--
-- Data for Name: sms_coupon_spu_relation; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sms_coupon_spu_relation (id, coupon_id, spu_id, spu_name) FROM stdin;
\.


--
-- Data for Name: sms_home_adv; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sms_home_adv (id, name, pic, start_time, end_time, status, click_count, url, note, sort, publisher_id, auth_id) FROM stdin;
\.


--
-- Data for Name: sms_home_subject; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sms_home_subject (id, name, title, sub_title, status, url, sort, img) FROM stdin;
\.


--
-- Data for Name: sms_home_subject_spu; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sms_home_subject_spu (id, name, subject_id, spu_id, sort) FROM stdin;
\.


--
-- Data for Name: sms_member_price; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sms_member_price (id, sku_id, member_level_id, member_level_name, member_price, add_other) FROM stdin;
\.


--
-- Data for Name: sms_seckill_promotion; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sms_seckill_promotion (id, title, start_time, end_time, status, create_time, user_id) FROM stdin;
\.


--
-- Data for Name: sms_seckill_session; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sms_seckill_session (id, name, start_time, end_time, status, create_time) FROM stdin;
\.


--
-- Data for Name: sms_seckill_sku_notice; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sms_seckill_sku_notice (id, member_id, sku_id, session_id, subcribe_time, send_time, notice_type) FROM stdin;
\.


--
-- Data for Name: sms_seckill_sku_relation; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sms_seckill_sku_relation (id, promotion_id, promotion_session_id, sku_id, seckill_price, seckill_count, seckill_limit, seckill_sort) FROM stdin;
\.


--
-- Data for Name: sms_sku_full_reduction; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sms_sku_full_reduction (id, sku_id, full_price, reduce_price, add_other) FROM stdin;
\.


--
-- Data for Name: sms_sku_ladder; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sms_sku_ladder (id, sku_id, full_count, discount, price, add_other) FROM stdin;
\.


--
-- Data for Name: sms_spu_bounds; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sms_spu_bounds (id, spu_id, grow_bounds, buy_bounds, work) FROM stdin;
\.


--
-- Data for Name: undo_log; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.undo_log (id, branch_id, xid, context, rollback_info, log_status, log_created, log_modified, ext) FROM stdin;
\.


--
-- Name: sms_coupon_history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sms_coupon_history_id_seq', 1, true);


--
-- Name: sms_coupon_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sms_coupon_id_seq', 1, true);


--
-- Name: sms_coupon_spu_category_relation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sms_coupon_spu_category_relation_id_seq', 1, true);


--
-- Name: sms_coupon_spu_relation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sms_coupon_spu_relation_id_seq', 1, true);


--
-- Name: sms_home_adv_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sms_home_adv_id_seq', 1, true);


--
-- Name: sms_home_subject_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sms_home_subject_id_seq', 1, true);


--
-- Name: sms_home_subject_spu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sms_home_subject_spu_id_seq', 1, true);


--
-- Name: sms_member_price_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sms_member_price_id_seq', 1, true);


--
-- Name: sms_seckill_promotion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sms_seckill_promotion_id_seq', 1, true);


--
-- Name: sms_seckill_session_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sms_seckill_session_id_seq', 1, true);


--
-- Name: sms_seckill_sku_notice_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sms_seckill_sku_notice_id_seq', 1, true);


--
-- Name: sms_seckill_sku_relation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sms_seckill_sku_relation_id_seq', 1, true);


--
-- Name: sms_sku_full_reduction_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sms_sku_full_reduction_id_seq', 1, true);


--
-- Name: sms_sku_ladder_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sms_sku_ladder_id_seq', 1, true);


--
-- Name: sms_spu_bounds_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sms_spu_bounds_id_seq', 1, true);


--
-- Name: undo_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.undo_log_id_seq', 1, true);


--
-- Name: sms_coupon idx_25143_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_coupon
    ADD CONSTRAINT idx_25143_primary PRIMARY KEY (id);


--
-- Name: sms_coupon_history idx_25150_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_coupon_history
    ADD CONSTRAINT idx_25150_primary PRIMARY KEY (id);


--
-- Name: sms_coupon_spu_category_relation idx_25155_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_coupon_spu_category_relation
    ADD CONSTRAINT idx_25155_primary PRIMARY KEY (id);


--
-- Name: sms_coupon_spu_relation idx_25160_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_coupon_spu_relation
    ADD CONSTRAINT idx_25160_primary PRIMARY KEY (id);


--
-- Name: sms_home_adv idx_25165_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_home_adv
    ADD CONSTRAINT idx_25165_primary PRIMARY KEY (id);


--
-- Name: sms_home_subject idx_25172_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_home_subject
    ADD CONSTRAINT idx_25172_primary PRIMARY KEY (id);


--
-- Name: sms_home_subject_spu idx_25179_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_home_subject_spu
    ADD CONSTRAINT idx_25179_primary PRIMARY KEY (id);


--
-- Name: sms_member_price idx_25184_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_member_price
    ADD CONSTRAINT idx_25184_primary PRIMARY KEY (id);


--
-- Name: sms_seckill_promotion idx_25189_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_seckill_promotion
    ADD CONSTRAINT idx_25189_primary PRIMARY KEY (id);


--
-- Name: sms_seckill_session idx_25194_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_seckill_session
    ADD CONSTRAINT idx_25194_primary PRIMARY KEY (id);


--
-- Name: sms_seckill_sku_notice idx_25199_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_seckill_sku_notice
    ADD CONSTRAINT idx_25199_primary PRIMARY KEY (id);


--
-- Name: sms_seckill_sku_relation idx_25204_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_seckill_sku_relation
    ADD CONSTRAINT idx_25204_primary PRIMARY KEY (id);


--
-- Name: sms_sku_full_reduction idx_25209_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_sku_full_reduction
    ADD CONSTRAINT idx_25209_primary PRIMARY KEY (id);


--
-- Name: sms_sku_ladder idx_25214_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_sku_ladder
    ADD CONSTRAINT idx_25214_primary PRIMARY KEY (id);


--
-- Name: sms_spu_bounds idx_25219_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sms_spu_bounds
    ADD CONSTRAINT idx_25219_primary PRIMARY KEY (id);


--
-- Name: undo_log idx_25224_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.undo_log
    ADD CONSTRAINT idx_25224_primary PRIMARY KEY (id);


--
-- Name: idx_25224_ux_undo_log; Type: INDEX; Schema: public; Owner: faris
--

CREATE UNIQUE INDEX idx_25224_ux_undo_log ON public.undo_log USING btree (xid, branch_id);


--
-- PostgreSQL database dump complete
--

\unrestrict mxRna34d9GBuJXhdYvbdgMGnCWUAC4KjFFRj2lyCdT77tVuhW7TI0AFvPFVG7zd

