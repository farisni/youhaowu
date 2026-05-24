--
-- PostgreSQL database dump
--

\restrict UQP5EJaNODzX2VCxNXVN2x8hdMPoNv05fQTlhyeNCQzdbnheQyGCDEWhInWFZz4

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
-- Name: ums_growth_change_history; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.ums_growth_change_history (
    id bigint NOT NULL,
    member_id bigint,
    create_time timestamp with time zone,
    change_count integer,
    note character varying(255),
    source_type smallint
);


ALTER TABLE public.ums_growth_change_history OWNER TO faris;

--
-- Name: TABLE ums_growth_change_history; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.ums_growth_change_history IS '成长值变化历史记录';


--
-- Name: COLUMN ums_growth_change_history.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_growth_change_history.id IS 'id';


--
-- Name: COLUMN ums_growth_change_history.member_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_growth_change_history.member_id IS 'member_id';


--
-- Name: COLUMN ums_growth_change_history.create_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_growth_change_history.create_time IS 'create_time';


--
-- Name: COLUMN ums_growth_change_history.change_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_growth_change_history.change_count IS '改变的值（正负计数）';


--
-- Name: COLUMN ums_growth_change_history.source_type; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_growth_change_history.source_type IS '积分来源[0-购物，1-管理员修改]';


--
-- Name: ums_growth_change_history_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.ums_growth_change_history_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ums_growth_change_history_id_seq OWNER TO faris;

--
-- Name: ums_growth_change_history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.ums_growth_change_history_id_seq OWNED BY public.ums_growth_change_history.id;


--
-- Name: ums_integration_change_history; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.ums_integration_change_history (
    id bigint NOT NULL,
    member_id bigint,
    create_time timestamp with time zone,
    change_count integer,
    note character varying(255),
    source_tyoe smallint
);


ALTER TABLE public.ums_integration_change_history OWNER TO faris;

--
-- Name: TABLE ums_integration_change_history; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.ums_integration_change_history IS '积分变化历史记录';


--
-- Name: COLUMN ums_integration_change_history.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_integration_change_history.id IS 'id';


--
-- Name: COLUMN ums_integration_change_history.member_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_integration_change_history.member_id IS 'member_id';


--
-- Name: COLUMN ums_integration_change_history.create_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_integration_change_history.create_time IS 'create_time';


--
-- Name: COLUMN ums_integration_change_history.change_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_integration_change_history.change_count IS '变化的值';


--
-- Name: COLUMN ums_integration_change_history.note; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_integration_change_history.note IS '备注';


--
-- Name: COLUMN ums_integration_change_history.source_tyoe; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_integration_change_history.source_tyoe IS '来源[0->购物；1->管理员修改;2->活动]';


--
-- Name: ums_integration_change_history_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.ums_integration_change_history_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ums_integration_change_history_id_seq OWNER TO faris;

--
-- Name: ums_integration_change_history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.ums_integration_change_history_id_seq OWNED BY public.ums_integration_change_history.id;


--
-- Name: ums_member; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.ums_member (
    id bigint NOT NULL,
    level_id bigint,
    username character varying(64),
    password character varying(64),
    nickname character varying(64),
    mobile character varying(20),
    email character varying(64),
    header character varying(500),
    gender smallint,
    birth date,
    city character varying(500),
    job character varying(255),
    sign character varying(255),
    source_type smallint,
    integration integer,
    growth integer,
    status smallint,
    create_time timestamp with time zone,
    social_uid character varying(255),
    access_token character varying(255),
    expires_in character varying(255)
);


ALTER TABLE public.ums_member OWNER TO faris;

--
-- Name: TABLE ums_member; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.ums_member IS '会员';


--
-- Name: COLUMN ums_member.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.id IS 'id';


--
-- Name: COLUMN ums_member.level_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.level_id IS '会员等级id';


--
-- Name: COLUMN ums_member.username; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.username IS '用户名';


--
-- Name: COLUMN ums_member.password; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.password IS '密码';


--
-- Name: COLUMN ums_member.nickname; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.nickname IS '昵称';


--
-- Name: COLUMN ums_member.mobile; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.mobile IS '手机号码';


--
-- Name: COLUMN ums_member.email; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.email IS '邮箱';


--
-- Name: COLUMN ums_member.header; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.header IS '头像';


--
-- Name: COLUMN ums_member.gender; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.gender IS '性别';


--
-- Name: COLUMN ums_member.birth; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.birth IS '生日';


--
-- Name: COLUMN ums_member.city; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.city IS '所在城市';


--
-- Name: COLUMN ums_member.job; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.job IS '职业';


--
-- Name: COLUMN ums_member.sign; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.sign IS '个性签名';


--
-- Name: COLUMN ums_member.source_type; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.source_type IS '用户来源';


--
-- Name: COLUMN ums_member.integration; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.integration IS '积分';


--
-- Name: COLUMN ums_member.growth; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.growth IS '成长值';


--
-- Name: COLUMN ums_member.status; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.status IS '启用状态';


--
-- Name: COLUMN ums_member.create_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.create_time IS '注册时间';


--
-- Name: COLUMN ums_member.social_uid; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.social_uid IS '社交用户的唯一id';


--
-- Name: COLUMN ums_member.access_token; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.access_token IS '访问令牌';


--
-- Name: COLUMN ums_member.expires_in; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member.expires_in IS '访问令牌的时间';


--
-- Name: ums_member_collect_spu; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.ums_member_collect_spu (
    id bigint NOT NULL,
    member_id bigint,
    spu_id bigint,
    spu_name character varying(500),
    spu_img character varying(500),
    create_time timestamp with time zone
);


ALTER TABLE public.ums_member_collect_spu OWNER TO faris;

--
-- Name: TABLE ums_member_collect_spu; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.ums_member_collect_spu IS '会员收藏的商品';


--
-- Name: COLUMN ums_member_collect_spu.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_collect_spu.id IS 'id';


--
-- Name: COLUMN ums_member_collect_spu.member_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_collect_spu.member_id IS '会员id';


--
-- Name: COLUMN ums_member_collect_spu.spu_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_collect_spu.spu_id IS 'spu_id';


--
-- Name: COLUMN ums_member_collect_spu.spu_name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_collect_spu.spu_name IS 'spu_name';


--
-- Name: COLUMN ums_member_collect_spu.spu_img; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_collect_spu.spu_img IS 'spu_img';


--
-- Name: COLUMN ums_member_collect_spu.create_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_collect_spu.create_time IS 'create_time';


--
-- Name: ums_member_collect_subject; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.ums_member_collect_subject (
    id bigint NOT NULL,
    subject_id bigint,
    subject_name character varying(255),
    subject_img character varying(500),
    subject_urll character varying(500)
);


ALTER TABLE public.ums_member_collect_subject OWNER TO faris;

--
-- Name: TABLE ums_member_collect_subject; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.ums_member_collect_subject IS '会员收藏的专题活动';


--
-- Name: COLUMN ums_member_collect_subject.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_collect_subject.id IS 'id';


--
-- Name: COLUMN ums_member_collect_subject.subject_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_collect_subject.subject_id IS 'subject_id';


--
-- Name: COLUMN ums_member_collect_subject.subject_name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_collect_subject.subject_name IS 'subject_name';


--
-- Name: COLUMN ums_member_collect_subject.subject_img; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_collect_subject.subject_img IS 'subject_img';


--
-- Name: COLUMN ums_member_collect_subject.subject_urll; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_collect_subject.subject_urll IS '活动url';


--
-- Name: ums_member_collect_subject_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.ums_member_collect_subject_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ums_member_collect_subject_id_seq OWNER TO faris;

--
-- Name: ums_member_collect_subject_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.ums_member_collect_subject_id_seq OWNED BY public.ums_member_collect_subject.id;


--
-- Name: ums_member_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.ums_member_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ums_member_id_seq OWNER TO faris;

--
-- Name: ums_member_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.ums_member_id_seq OWNED BY public.ums_member.id;


--
-- Name: ums_member_level; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.ums_member_level (
    id bigint NOT NULL,
    name character varying(100),
    growth_point integer,
    default_status smallint,
    free_freight_point numeric(18,4),
    comment_growth_point integer,
    priviledge_free_freight smallint,
    priviledge_member_price smallint,
    priviledge_birthday smallint,
    note character varying(255)
);


ALTER TABLE public.ums_member_level OWNER TO faris;

--
-- Name: TABLE ums_member_level; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.ums_member_level IS '会员等级';


--
-- Name: COLUMN ums_member_level.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_level.id IS 'id';


--
-- Name: COLUMN ums_member_level.name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_level.name IS '等级名称';


--
-- Name: COLUMN ums_member_level.growth_point; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_level.growth_point IS '等级需要的成长值';


--
-- Name: COLUMN ums_member_level.default_status; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_level.default_status IS '是否为默认等级[0->不是；1->是]';


--
-- Name: COLUMN ums_member_level.free_freight_point; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_level.free_freight_point IS '免运费标准';


--
-- Name: COLUMN ums_member_level.comment_growth_point; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_level.comment_growth_point IS '每次评价获取的成长值';


--
-- Name: COLUMN ums_member_level.priviledge_free_freight; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_level.priviledge_free_freight IS '是否有免邮特权';


--
-- Name: COLUMN ums_member_level.priviledge_member_price; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_level.priviledge_member_price IS '是否有会员价格特权';


--
-- Name: COLUMN ums_member_level.priviledge_birthday; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_level.priviledge_birthday IS '是否有生日特权';


--
-- Name: COLUMN ums_member_level.note; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_level.note IS '备注';


--
-- Name: ums_member_level_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.ums_member_level_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ums_member_level_id_seq OWNER TO faris;

--
-- Name: ums_member_level_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.ums_member_level_id_seq OWNED BY public.ums_member_level.id;


--
-- Name: ums_member_login_log; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.ums_member_login_log (
    id bigint NOT NULL,
    member_id bigint,
    create_time timestamp with time zone,
    ip character varying(64),
    city character varying(64),
    login_type boolean
);


ALTER TABLE public.ums_member_login_log OWNER TO faris;

--
-- Name: TABLE ums_member_login_log; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.ums_member_login_log IS '会员登录记录';


--
-- Name: COLUMN ums_member_login_log.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_login_log.id IS 'id';


--
-- Name: COLUMN ums_member_login_log.member_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_login_log.member_id IS 'member_id';


--
-- Name: COLUMN ums_member_login_log.create_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_login_log.create_time IS '创建时间';


--
-- Name: COLUMN ums_member_login_log.ip; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_login_log.ip IS 'ip';


--
-- Name: COLUMN ums_member_login_log.city; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_login_log.city IS 'city';


--
-- Name: COLUMN ums_member_login_log.login_type; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_login_log.login_type IS '登录类型[1-web，2-app]';


--
-- Name: ums_member_login_log_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.ums_member_login_log_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ums_member_login_log_id_seq OWNER TO faris;

--
-- Name: ums_member_login_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.ums_member_login_log_id_seq OWNED BY public.ums_member_login_log.id;


--
-- Name: ums_member_receive_address; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.ums_member_receive_address (
    id bigint NOT NULL,
    member_id bigint,
    name character varying(255),
    phone character varying(64),
    post_code character varying(64),
    province character varying(100),
    city character varying(100),
    region character varying(100),
    detail_address character varying(255),
    areacode character varying(15),
    default_status boolean
);


ALTER TABLE public.ums_member_receive_address OWNER TO faris;

--
-- Name: TABLE ums_member_receive_address; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.ums_member_receive_address IS '会员收货地址';


--
-- Name: COLUMN ums_member_receive_address.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_receive_address.id IS 'id';


--
-- Name: COLUMN ums_member_receive_address.member_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_receive_address.member_id IS 'member_id';


--
-- Name: COLUMN ums_member_receive_address.name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_receive_address.name IS '收货人姓名';


--
-- Name: COLUMN ums_member_receive_address.phone; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_receive_address.phone IS '电话';


--
-- Name: COLUMN ums_member_receive_address.post_code; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_receive_address.post_code IS '邮政编码';


--
-- Name: COLUMN ums_member_receive_address.province; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_receive_address.province IS '省份/直辖市';


--
-- Name: COLUMN ums_member_receive_address.city; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_receive_address.city IS '城市';


--
-- Name: COLUMN ums_member_receive_address.region; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_receive_address.region IS '区';


--
-- Name: COLUMN ums_member_receive_address.detail_address; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_receive_address.detail_address IS '详细地址(街道)';


--
-- Name: COLUMN ums_member_receive_address.areacode; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_receive_address.areacode IS '省市区代码';


--
-- Name: COLUMN ums_member_receive_address.default_status; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_receive_address.default_status IS '是否默认';


--
-- Name: ums_member_receive_address_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.ums_member_receive_address_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ums_member_receive_address_id_seq OWNER TO faris;

--
-- Name: ums_member_receive_address_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.ums_member_receive_address_id_seq OWNED BY public.ums_member_receive_address.id;


--
-- Name: ums_member_statistics_info; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.ums_member_statistics_info (
    id bigint NOT NULL,
    member_id bigint,
    consume_amount numeric(18,4),
    coupon_amount numeric(18,4),
    order_count integer,
    coupon_count integer,
    comment_count integer,
    return_order_count integer,
    login_count integer,
    attend_count integer,
    fans_count integer,
    collect_product_count integer,
    collect_subject_count integer,
    collect_comment_count integer,
    invite_friend_count integer
);


ALTER TABLE public.ums_member_statistics_info OWNER TO faris;

--
-- Name: TABLE ums_member_statistics_info; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.ums_member_statistics_info IS '会员统计信息';


--
-- Name: COLUMN ums_member_statistics_info.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_statistics_info.id IS 'id';


--
-- Name: COLUMN ums_member_statistics_info.member_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_statistics_info.member_id IS '会员id';


--
-- Name: COLUMN ums_member_statistics_info.consume_amount; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_statistics_info.consume_amount IS '累计消费金额';


--
-- Name: COLUMN ums_member_statistics_info.coupon_amount; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_statistics_info.coupon_amount IS '累计优惠金额';


--
-- Name: COLUMN ums_member_statistics_info.order_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_statistics_info.order_count IS '订单数量';


--
-- Name: COLUMN ums_member_statistics_info.coupon_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_statistics_info.coupon_count IS '优惠券数量';


--
-- Name: COLUMN ums_member_statistics_info.comment_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_statistics_info.comment_count IS '评价数';


--
-- Name: COLUMN ums_member_statistics_info.return_order_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_statistics_info.return_order_count IS '退货数量';


--
-- Name: COLUMN ums_member_statistics_info.login_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_statistics_info.login_count IS '登录次数';


--
-- Name: COLUMN ums_member_statistics_info.attend_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_statistics_info.attend_count IS '关注数量';


--
-- Name: COLUMN ums_member_statistics_info.fans_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_statistics_info.fans_count IS '粉丝数量';


--
-- Name: COLUMN ums_member_statistics_info.collect_product_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_statistics_info.collect_product_count IS '收藏的商品数量';


--
-- Name: COLUMN ums_member_statistics_info.collect_subject_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_statistics_info.collect_subject_count IS '收藏的专题活动数量';


--
-- Name: COLUMN ums_member_statistics_info.collect_comment_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_statistics_info.collect_comment_count IS '收藏的评论数量';


--
-- Name: COLUMN ums_member_statistics_info.invite_friend_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.ums_member_statistics_info.invite_friend_count IS '邀请的朋友数量';


--
-- Name: ums_member_statistics_info_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.ums_member_statistics_info_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ums_member_statistics_info_id_seq OWNER TO faris;

--
-- Name: ums_member_statistics_info_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.ums_member_statistics_info_id_seq OWNED BY public.ums_member_statistics_info.id;


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
-- Name: ums_growth_change_history id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.ums_growth_change_history ALTER COLUMN id SET DEFAULT nextval('public.ums_growth_change_history_id_seq'::regclass);


--
-- Name: ums_integration_change_history id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.ums_integration_change_history ALTER COLUMN id SET DEFAULT nextval('public.ums_integration_change_history_id_seq'::regclass);


--
-- Name: ums_member id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.ums_member ALTER COLUMN id SET DEFAULT nextval('public.ums_member_id_seq'::regclass);


--
-- Name: ums_member_collect_subject id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.ums_member_collect_subject ALTER COLUMN id SET DEFAULT nextval('public.ums_member_collect_subject_id_seq'::regclass);


--
-- Name: ums_member_level id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.ums_member_level ALTER COLUMN id SET DEFAULT nextval('public.ums_member_level_id_seq'::regclass);


--
-- Name: ums_member_login_log id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.ums_member_login_log ALTER COLUMN id SET DEFAULT nextval('public.ums_member_login_log_id_seq'::regclass);


--
-- Name: ums_member_receive_address id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.ums_member_receive_address ALTER COLUMN id SET DEFAULT nextval('public.ums_member_receive_address_id_seq'::regclass);


--
-- Name: ums_member_statistics_info id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.ums_member_statistics_info ALTER COLUMN id SET DEFAULT nextval('public.ums_member_statistics_info_id_seq'::regclass);


--
-- Name: undo_log id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.undo_log ALTER COLUMN id SET DEFAULT nextval('public.undo_log_id_seq'::regclass);


--
-- Data for Name: ums_growth_change_history; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.ums_growth_change_history (id, member_id, create_time, change_count, note, source_type) FROM stdin;
\.


--
-- Data for Name: ums_integration_change_history; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.ums_integration_change_history (id, member_id, create_time, change_count, note, source_tyoe) FROM stdin;
\.


--
-- Data for Name: ums_member; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.ums_member (id, level_id, username, password, nickname, mobile, email, header, gender, birth, city, job, sign, source_type, integration, growth, status, create_time, social_uid, access_token, expires_in) FROM stdin;
\.


--
-- Data for Name: ums_member_collect_spu; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.ums_member_collect_spu (id, member_id, spu_id, spu_name, spu_img, create_time) FROM stdin;
\.


--
-- Data for Name: ums_member_collect_subject; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.ums_member_collect_subject (id, subject_id, subject_name, subject_img, subject_urll) FROM stdin;
\.


--
-- Data for Name: ums_member_level; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.ums_member_level (id, name, growth_point, default_status, free_freight_point, comment_growth_point, priviledge_free_freight, priviledge_member_price, priviledge_birthday, note) FROM stdin;
\.


--
-- Data for Name: ums_member_login_log; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.ums_member_login_log (id, member_id, create_time, ip, city, login_type) FROM stdin;
\.


--
-- Data for Name: ums_member_receive_address; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.ums_member_receive_address (id, member_id, name, phone, post_code, province, city, region, detail_address, areacode, default_status) FROM stdin;
\.


--
-- Data for Name: ums_member_statistics_info; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.ums_member_statistics_info (id, member_id, consume_amount, coupon_amount, order_count, coupon_count, comment_count, return_order_count, login_count, attend_count, fans_count, collect_product_count, collect_subject_count, collect_comment_count, invite_friend_count) FROM stdin;
\.


--
-- Data for Name: undo_log; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.undo_log (id, branch_id, xid, context, rollback_info, log_status, log_created, log_modified, ext) FROM stdin;
\.


--
-- Name: ums_growth_change_history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.ums_growth_change_history_id_seq', 1, true);


--
-- Name: ums_integration_change_history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.ums_integration_change_history_id_seq', 1, true);


--
-- Name: ums_member_collect_subject_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.ums_member_collect_subject_id_seq', 1, true);


--
-- Name: ums_member_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.ums_member_id_seq', 1, true);


--
-- Name: ums_member_level_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.ums_member_level_id_seq', 1, true);


--
-- Name: ums_member_login_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.ums_member_login_log_id_seq', 1, true);


--
-- Name: ums_member_receive_address_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.ums_member_receive_address_id_seq', 1, true);


--
-- Name: ums_member_statistics_info_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.ums_member_statistics_info_id_seq', 1, true);


--
-- Name: undo_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.undo_log_id_seq', 1, true);


--
-- Name: ums_growth_change_history idx_25423_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.ums_growth_change_history
    ADD CONSTRAINT idx_25423_primary PRIMARY KEY (id);


--
-- Name: ums_integration_change_history idx_25428_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.ums_integration_change_history
    ADD CONSTRAINT idx_25428_primary PRIMARY KEY (id);


--
-- Name: ums_member idx_25433_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.ums_member
    ADD CONSTRAINT idx_25433_primary PRIMARY KEY (id);


--
-- Name: ums_member_collect_spu idx_25439_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.ums_member_collect_spu
    ADD CONSTRAINT idx_25439_primary PRIMARY KEY (id);


--
-- Name: ums_member_collect_subject idx_25445_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.ums_member_collect_subject
    ADD CONSTRAINT idx_25445_primary PRIMARY KEY (id);


--
-- Name: ums_member_level idx_25452_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.ums_member_level
    ADD CONSTRAINT idx_25452_primary PRIMARY KEY (id);


--
-- Name: ums_member_login_log idx_25457_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.ums_member_login_log
    ADD CONSTRAINT idx_25457_primary PRIMARY KEY (id);


--
-- Name: ums_member_receive_address idx_25462_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.ums_member_receive_address
    ADD CONSTRAINT idx_25462_primary PRIMARY KEY (id);


--
-- Name: ums_member_statistics_info idx_25469_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.ums_member_statistics_info
    ADD CONSTRAINT idx_25469_primary PRIMARY KEY (id);


--
-- Name: undo_log idx_25474_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.undo_log
    ADD CONSTRAINT idx_25474_primary PRIMARY KEY (id);


--
-- Name: idx_25474_ux_undo_log; Type: INDEX; Schema: public; Owner: faris
--

CREATE UNIQUE INDEX idx_25474_ux_undo_log ON public.undo_log USING btree (xid, branch_id);


--
-- PostgreSQL database dump complete
--

\unrestrict UQP5EJaNODzX2VCxNXVN2x8hdMPoNv05fQTlhyeNCQzdbnheQyGCDEWhInWFZz4

