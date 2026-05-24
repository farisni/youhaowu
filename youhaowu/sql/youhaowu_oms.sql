--
-- PostgreSQL database dump
--

\restrict lBc5fimSIRL2LBoKOLIPffUJdCjIBLhR4rdj9pe80soJQCMogrkze5ARGbY2FF1

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
-- Name: mq_message; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.mq_message (
    message_id character varying(64) NOT NULL,
    content text,
    to_exchane character varying(255),
    routing_key character varying(255),
    class_type character varying(255),
    message_status integer DEFAULT 0,
    create_time timestamp with time zone,
    update_time timestamp with time zone
);


ALTER TABLE public.mq_message OWNER TO faris;

--
-- Name: COLUMN mq_message.message_status; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.mq_message.message_status IS '0-新建 1-已发送 2-错误抵达 3-已抵达';


--
-- Name: oms_order; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.oms_order (
    id bigint NOT NULL,
    member_id bigint,
    order_sn character varying(64),
    coupon_id bigint,
    create_time timestamp with time zone,
    member_username character varying(200),
    total_amount numeric(18,4),
    pay_amount numeric(18,4),
    freight_amount numeric(18,4),
    promotion_amount numeric(18,4),
    integration_amount numeric(18,4),
    coupon_amount numeric(18,4),
    discount_amount numeric(18,4),
    pay_type smallint,
    source_type smallint,
    status smallint,
    delivery_company character varying(64),
    delivery_sn character varying(64),
    auto_confirm_day integer,
    integration integer,
    growth integer,
    bill_type smallint,
    bill_header character varying(255),
    bill_content character varying(255),
    bill_receiver_phone character varying(32),
    bill_receiver_email character varying(64),
    receiver_name character varying(100),
    receiver_phone character varying(32),
    receiver_post_code character varying(32),
    receiver_province character varying(32),
    receiver_city character varying(32),
    receiver_region character varying(32),
    receiver_detail_address character varying(200),
    note character varying(500),
    confirm_status smallint,
    delete_status smallint,
    use_integration integer,
    payment_time timestamp with time zone,
    delivery_time timestamp with time zone,
    receive_time timestamp with time zone,
    comment_time timestamp with time zone,
    modify_time timestamp with time zone
);


ALTER TABLE public.oms_order OWNER TO faris;

--
-- Name: TABLE oms_order; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.oms_order IS '订单';


--
-- Name: COLUMN oms_order.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.id IS 'id';


--
-- Name: COLUMN oms_order.member_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.member_id IS 'member_id';


--
-- Name: COLUMN oms_order.order_sn; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.order_sn IS '订单号';


--
-- Name: COLUMN oms_order.coupon_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.coupon_id IS '使用的优惠券';


--
-- Name: COLUMN oms_order.create_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.create_time IS 'create_time';


--
-- Name: COLUMN oms_order.member_username; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.member_username IS '用户名';


--
-- Name: COLUMN oms_order.total_amount; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.total_amount IS '订单总额';


--
-- Name: COLUMN oms_order.pay_amount; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.pay_amount IS '应付总额';


--
-- Name: COLUMN oms_order.freight_amount; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.freight_amount IS '运费金额';


--
-- Name: COLUMN oms_order.promotion_amount; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.promotion_amount IS '促销优化金额（促销价、满减、阶梯价）';


--
-- Name: COLUMN oms_order.integration_amount; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.integration_amount IS '积分抵扣金额';


--
-- Name: COLUMN oms_order.coupon_amount; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.coupon_amount IS '优惠券抵扣金额';


--
-- Name: COLUMN oms_order.discount_amount; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.discount_amount IS '后台调整订单使用的折扣金额';


--
-- Name: COLUMN oms_order.pay_type; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.pay_type IS '支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】';


--
-- Name: COLUMN oms_order.source_type; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.source_type IS '订单来源[0->PC订单；1->app订单]';


--
-- Name: COLUMN oms_order.status; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.status IS '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】';


--
-- Name: COLUMN oms_order.delivery_company; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.delivery_company IS '物流公司(配送方式)';


--
-- Name: COLUMN oms_order.delivery_sn; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.delivery_sn IS '物流单号';


--
-- Name: COLUMN oms_order.auto_confirm_day; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.auto_confirm_day IS '自动确认时间（天）';


--
-- Name: COLUMN oms_order.integration; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.integration IS '可以获得的积分';


--
-- Name: COLUMN oms_order.growth; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.growth IS '可以获得的成长值';


--
-- Name: COLUMN oms_order.bill_type; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.bill_type IS '发票类型[0->不开发票；1->电子发票；2->纸质发票]';


--
-- Name: COLUMN oms_order.bill_header; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.bill_header IS '发票抬头';


--
-- Name: COLUMN oms_order.bill_content; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.bill_content IS '发票内容';


--
-- Name: COLUMN oms_order.bill_receiver_phone; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.bill_receiver_phone IS '收票人电话';


--
-- Name: COLUMN oms_order.bill_receiver_email; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.bill_receiver_email IS '收票人邮箱';


--
-- Name: COLUMN oms_order.receiver_name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.receiver_name IS '收货人姓名';


--
-- Name: COLUMN oms_order.receiver_phone; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.receiver_phone IS '收货人电话';


--
-- Name: COLUMN oms_order.receiver_post_code; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.receiver_post_code IS '收货人邮编';


--
-- Name: COLUMN oms_order.receiver_province; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.receiver_province IS '省份/直辖市';


--
-- Name: COLUMN oms_order.receiver_city; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.receiver_city IS '城市';


--
-- Name: COLUMN oms_order.receiver_region; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.receiver_region IS '区';


--
-- Name: COLUMN oms_order.receiver_detail_address; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.receiver_detail_address IS '详细地址';


--
-- Name: COLUMN oms_order.note; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.note IS '订单备注';


--
-- Name: COLUMN oms_order.confirm_status; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.confirm_status IS '确认收货状态[0->未确认；1->已确认]';


--
-- Name: COLUMN oms_order.delete_status; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.delete_status IS '删除状态【0->未删除；1->已删除】';


--
-- Name: COLUMN oms_order.use_integration; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.use_integration IS '下单时使用的积分';


--
-- Name: COLUMN oms_order.payment_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.payment_time IS '支付时间';


--
-- Name: COLUMN oms_order.delivery_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.delivery_time IS '发货时间';


--
-- Name: COLUMN oms_order.receive_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.receive_time IS '确认收货时间';


--
-- Name: COLUMN oms_order.comment_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.comment_time IS '评价时间';


--
-- Name: COLUMN oms_order.modify_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order.modify_time IS '修改时间';


--
-- Name: oms_order_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.oms_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.oms_order_id_seq OWNER TO faris;

--
-- Name: oms_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.oms_order_id_seq OWNED BY public.oms_order.id;


--
-- Name: oms_order_item; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.oms_order_item (
    id bigint NOT NULL,
    order_id bigint,
    order_sn character varying(64),
    spu_id bigint,
    spu_name character varying(255),
    spu_pic character varying(500),
    spu_brand character varying(200),
    category_id bigint,
    sku_id bigint,
    sku_name character varying(255),
    sku_pic character varying(500),
    sku_price numeric(18,4),
    sku_quantity integer,
    sku_attrs_vals character varying(500),
    promotion_amount numeric(18,4),
    coupon_amount numeric(18,4),
    integration_amount numeric(18,4),
    real_amount numeric(18,4),
    gift_integration integer,
    gift_growth integer
);


ALTER TABLE public.oms_order_item OWNER TO faris;

--
-- Name: TABLE oms_order_item; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.oms_order_item IS '订单项信息';


--
-- Name: COLUMN oms_order_item.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_item.id IS 'id';


--
-- Name: COLUMN oms_order_item.order_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_item.order_id IS 'order_id';


--
-- Name: COLUMN oms_order_item.order_sn; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_item.order_sn IS 'order_sn';


--
-- Name: COLUMN oms_order_item.spu_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_item.spu_id IS 'spu_id';


--
-- Name: COLUMN oms_order_item.spu_name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_item.spu_name IS 'spu_name';


--
-- Name: COLUMN oms_order_item.spu_pic; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_item.spu_pic IS 'spu_pic';


--
-- Name: COLUMN oms_order_item.spu_brand; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_item.spu_brand IS '品牌';


--
-- Name: COLUMN oms_order_item.category_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_item.category_id IS '商品分类id';


--
-- Name: COLUMN oms_order_item.sku_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_item.sku_id IS '商品sku编号';


--
-- Name: COLUMN oms_order_item.sku_name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_item.sku_name IS '商品sku名字';


--
-- Name: COLUMN oms_order_item.sku_pic; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_item.sku_pic IS '商品sku图片';


--
-- Name: COLUMN oms_order_item.sku_price; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_item.sku_price IS '商品sku价格';


--
-- Name: COLUMN oms_order_item.sku_quantity; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_item.sku_quantity IS '商品购买的数量';


--
-- Name: COLUMN oms_order_item.sku_attrs_vals; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_item.sku_attrs_vals IS '商品销售属性组合（JSON）';


--
-- Name: COLUMN oms_order_item.promotion_amount; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_item.promotion_amount IS '商品促销分解金额';


--
-- Name: COLUMN oms_order_item.coupon_amount; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_item.coupon_amount IS '优惠券优惠分解金额';


--
-- Name: COLUMN oms_order_item.integration_amount; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_item.integration_amount IS '积分优惠分解金额';


--
-- Name: COLUMN oms_order_item.real_amount; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_item.real_amount IS '该商品经过优惠后的分解金额';


--
-- Name: COLUMN oms_order_item.gift_integration; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_item.gift_integration IS '赠送积分';


--
-- Name: COLUMN oms_order_item.gift_growth; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_item.gift_growth IS '赠送成长值';


--
-- Name: oms_order_item_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.oms_order_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.oms_order_item_id_seq OWNER TO faris;

--
-- Name: oms_order_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.oms_order_item_id_seq OWNED BY public.oms_order_item.id;


--
-- Name: oms_order_operate_history; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.oms_order_operate_history (
    id bigint NOT NULL,
    order_id bigint,
    operate_man character varying(100),
    create_time timestamp with time zone,
    order_status smallint,
    note character varying(500)
);


ALTER TABLE public.oms_order_operate_history OWNER TO faris;

--
-- Name: TABLE oms_order_operate_history; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.oms_order_operate_history IS '订单操作历史记录';


--
-- Name: COLUMN oms_order_operate_history.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_operate_history.id IS 'id';


--
-- Name: COLUMN oms_order_operate_history.order_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_operate_history.order_id IS '订单id';


--
-- Name: COLUMN oms_order_operate_history.operate_man; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_operate_history.operate_man IS '操作人[用户；系统；后台管理员]';


--
-- Name: COLUMN oms_order_operate_history.create_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_operate_history.create_time IS '操作时间';


--
-- Name: COLUMN oms_order_operate_history.order_status; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_operate_history.order_status IS '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】';


--
-- Name: COLUMN oms_order_operate_history.note; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_operate_history.note IS '备注';


--
-- Name: oms_order_operate_history_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.oms_order_operate_history_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.oms_order_operate_history_id_seq OWNER TO faris;

--
-- Name: oms_order_operate_history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.oms_order_operate_history_id_seq OWNED BY public.oms_order_operate_history.id;


--
-- Name: oms_order_return_apply; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.oms_order_return_apply (
    id bigint NOT NULL,
    order_id bigint,
    sku_id bigint,
    order_sn character varying(64),
    create_time timestamp with time zone,
    member_username character varying(64),
    return_amount numeric(18,4),
    return_name character varying(100),
    return_phone character varying(20),
    status boolean,
    handle_time timestamp with time zone,
    sku_img character varying(500),
    sku_name character varying(200),
    sku_brand character varying(200),
    sku_attrs_vals character varying(500),
    sku_count integer,
    sku_price numeric(18,4),
    sku_real_price numeric(18,4),
    reason character varying(200),
    "description述" character varying(500),
    desc_pics character varying(2000),
    handle_note character varying(500),
    handle_man character varying(200),
    receive_man character varying(100),
    receive_time timestamp with time zone,
    receive_note character varying(500),
    receive_phone character varying(20),
    company_address character varying(500)
);


ALTER TABLE public.oms_order_return_apply OWNER TO faris;

--
-- Name: TABLE oms_order_return_apply; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.oms_order_return_apply IS '订单退货申请';


--
-- Name: COLUMN oms_order_return_apply.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.id IS 'id';


--
-- Name: COLUMN oms_order_return_apply.order_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.order_id IS 'order_id';


--
-- Name: COLUMN oms_order_return_apply.sku_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.sku_id IS '退货商品id';


--
-- Name: COLUMN oms_order_return_apply.order_sn; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.order_sn IS '订单编号';


--
-- Name: COLUMN oms_order_return_apply.create_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.create_time IS '申请时间';


--
-- Name: COLUMN oms_order_return_apply.member_username; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.member_username IS '会员用户名';


--
-- Name: COLUMN oms_order_return_apply.return_amount; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.return_amount IS '退款金额';


--
-- Name: COLUMN oms_order_return_apply.return_name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.return_name IS '退货人姓名';


--
-- Name: COLUMN oms_order_return_apply.return_phone; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.return_phone IS '退货人电话';


--
-- Name: COLUMN oms_order_return_apply.status; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.status IS '申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]';


--
-- Name: COLUMN oms_order_return_apply.handle_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.handle_time IS '处理时间';


--
-- Name: COLUMN oms_order_return_apply.sku_img; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.sku_img IS '商品图片';


--
-- Name: COLUMN oms_order_return_apply.sku_name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.sku_name IS '商品名称';


--
-- Name: COLUMN oms_order_return_apply.sku_brand; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.sku_brand IS '商品品牌';


--
-- Name: COLUMN oms_order_return_apply.sku_attrs_vals; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.sku_attrs_vals IS '商品销售属性(JSON)';


--
-- Name: COLUMN oms_order_return_apply.sku_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.sku_count IS '退货数量';


--
-- Name: COLUMN oms_order_return_apply.sku_price; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.sku_price IS '商品单价';


--
-- Name: COLUMN oms_order_return_apply.sku_real_price; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.sku_real_price IS '商品实际支付单价';


--
-- Name: COLUMN oms_order_return_apply.reason; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.reason IS '原因';


--
-- Name: COLUMN oms_order_return_apply."description述"; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply."description述" IS '描述';


--
-- Name: COLUMN oms_order_return_apply.desc_pics; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.desc_pics IS '凭证图片，以逗号隔开';


--
-- Name: COLUMN oms_order_return_apply.handle_note; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.handle_note IS '处理备注';


--
-- Name: COLUMN oms_order_return_apply.handle_man; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.handle_man IS '处理人员';


--
-- Name: COLUMN oms_order_return_apply.receive_man; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.receive_man IS '收货人';


--
-- Name: COLUMN oms_order_return_apply.receive_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.receive_time IS '收货时间';


--
-- Name: COLUMN oms_order_return_apply.receive_note; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.receive_note IS '收货备注';


--
-- Name: COLUMN oms_order_return_apply.receive_phone; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.receive_phone IS '收货电话';


--
-- Name: COLUMN oms_order_return_apply.company_address; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_apply.company_address IS '公司收货地址';


--
-- Name: oms_order_return_apply_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.oms_order_return_apply_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.oms_order_return_apply_id_seq OWNER TO faris;

--
-- Name: oms_order_return_apply_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.oms_order_return_apply_id_seq OWNED BY public.oms_order_return_apply.id;


--
-- Name: oms_order_return_reason; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.oms_order_return_reason (
    id bigint NOT NULL,
    name character varying(200),
    sort integer,
    status boolean,
    create_time timestamp with time zone
);


ALTER TABLE public.oms_order_return_reason OWNER TO faris;

--
-- Name: TABLE oms_order_return_reason; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.oms_order_return_reason IS '退货原因';


--
-- Name: COLUMN oms_order_return_reason.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_reason.id IS 'id';


--
-- Name: COLUMN oms_order_return_reason.name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_reason.name IS '退货原因名';


--
-- Name: COLUMN oms_order_return_reason.sort; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_reason.sort IS '排序';


--
-- Name: COLUMN oms_order_return_reason.status; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_reason.status IS '启用状态';


--
-- Name: COLUMN oms_order_return_reason.create_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_return_reason.create_time IS 'create_time';


--
-- Name: oms_order_return_reason_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.oms_order_return_reason_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.oms_order_return_reason_id_seq OWNER TO faris;

--
-- Name: oms_order_return_reason_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.oms_order_return_reason_id_seq OWNED BY public.oms_order_return_reason.id;


--
-- Name: oms_order_setting; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.oms_order_setting (
    id bigint NOT NULL,
    flash_order_overtime integer,
    normal_order_overtime integer,
    confirm_overtime integer,
    finish_overtime integer,
    comment_overtime integer,
    member_level smallint
);


ALTER TABLE public.oms_order_setting OWNER TO faris;

--
-- Name: TABLE oms_order_setting; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.oms_order_setting IS '订单配置信息';


--
-- Name: COLUMN oms_order_setting.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_setting.id IS 'id';


--
-- Name: COLUMN oms_order_setting.flash_order_overtime; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_setting.flash_order_overtime IS '秒杀订单超时关闭时间(分)';


--
-- Name: COLUMN oms_order_setting.normal_order_overtime; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_setting.normal_order_overtime IS '正常订单超时时间(分)';


--
-- Name: COLUMN oms_order_setting.confirm_overtime; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_setting.confirm_overtime IS '发货后自动确认收货时间（天）';


--
-- Name: COLUMN oms_order_setting.finish_overtime; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_setting.finish_overtime IS '自动完成交易时间，不能申请退货（天）';


--
-- Name: COLUMN oms_order_setting.comment_overtime; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_setting.comment_overtime IS '订单完成后自动好评时间（天）';


--
-- Name: COLUMN oms_order_setting.member_level; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_order_setting.member_level IS '会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】';


--
-- Name: oms_order_setting_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.oms_order_setting_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.oms_order_setting_id_seq OWNER TO faris;

--
-- Name: oms_order_setting_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.oms_order_setting_id_seq OWNED BY public.oms_order_setting.id;


--
-- Name: oms_payment_info; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.oms_payment_info (
    id bigint NOT NULL,
    order_sn character varying(64),
    order_id bigint,
    alipay_trade_no character varying(50),
    total_amount numeric(18,4),
    subject character varying(200),
    payment_status character varying(20),
    create_time timestamp with time zone,
    confirm_time timestamp with time zone,
    callback_content character varying(4000),
    callback_time timestamp with time zone
);


ALTER TABLE public.oms_payment_info OWNER TO faris;

--
-- Name: TABLE oms_payment_info; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.oms_payment_info IS '支付信息表';


--
-- Name: COLUMN oms_payment_info.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_payment_info.id IS 'id';


--
-- Name: COLUMN oms_payment_info.order_sn; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_payment_info.order_sn IS '订单号（对外业务号）';


--
-- Name: COLUMN oms_payment_info.order_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_payment_info.order_id IS '订单id';


--
-- Name: COLUMN oms_payment_info.alipay_trade_no; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_payment_info.alipay_trade_no IS '支付宝交易流水号';


--
-- Name: COLUMN oms_payment_info.total_amount; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_payment_info.total_amount IS '支付总金额';


--
-- Name: COLUMN oms_payment_info.subject; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_payment_info.subject IS '交易内容';


--
-- Name: COLUMN oms_payment_info.payment_status; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_payment_info.payment_status IS '支付状态';


--
-- Name: COLUMN oms_payment_info.create_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_payment_info.create_time IS '创建时间';


--
-- Name: COLUMN oms_payment_info.confirm_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_payment_info.confirm_time IS '确认时间';


--
-- Name: COLUMN oms_payment_info.callback_content; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_payment_info.callback_content IS '回调内容';


--
-- Name: COLUMN oms_payment_info.callback_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_payment_info.callback_time IS '回调时间';


--
-- Name: oms_payment_info_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.oms_payment_info_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.oms_payment_info_id_seq OWNER TO faris;

--
-- Name: oms_payment_info_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.oms_payment_info_id_seq OWNED BY public.oms_payment_info.id;


--
-- Name: oms_refund_info; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.oms_refund_info (
    id bigint NOT NULL,
    order_return_id bigint,
    refund numeric(18,4),
    refund_sn character varying(64),
    refund_status boolean,
    refund_channel smallint,
    refund_content character varying(5000)
);


ALTER TABLE public.oms_refund_info OWNER TO faris;

--
-- Name: TABLE oms_refund_info; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.oms_refund_info IS '退款信息';


--
-- Name: COLUMN oms_refund_info.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_refund_info.id IS 'id';


--
-- Name: COLUMN oms_refund_info.order_return_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_refund_info.order_return_id IS '退款的订单';


--
-- Name: COLUMN oms_refund_info.refund; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_refund_info.refund IS '退款金额';


--
-- Name: COLUMN oms_refund_info.refund_sn; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_refund_info.refund_sn IS '退款交易流水号';


--
-- Name: COLUMN oms_refund_info.refund_status; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_refund_info.refund_status IS '退款状态';


--
-- Name: COLUMN oms_refund_info.refund_channel; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.oms_refund_info.refund_channel IS '退款渠道[1-支付宝，2-微信，3-银联，4-汇款]';


--
-- Name: oms_refund_info_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.oms_refund_info_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.oms_refund_info_id_seq OWNER TO faris;

--
-- Name: oms_refund_info_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.oms_refund_info_id_seq OWNED BY public.oms_refund_info.id;


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
-- Name: oms_order id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.oms_order ALTER COLUMN id SET DEFAULT nextval('public.oms_order_id_seq'::regclass);


--
-- Name: oms_order_item id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.oms_order_item ALTER COLUMN id SET DEFAULT nextval('public.oms_order_item_id_seq'::regclass);


--
-- Name: oms_order_operate_history id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.oms_order_operate_history ALTER COLUMN id SET DEFAULT nextval('public.oms_order_operate_history_id_seq'::regclass);


--
-- Name: oms_order_return_apply id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.oms_order_return_apply ALTER COLUMN id SET DEFAULT nextval('public.oms_order_return_apply_id_seq'::regclass);


--
-- Name: oms_order_return_reason id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.oms_order_return_reason ALTER COLUMN id SET DEFAULT nextval('public.oms_order_return_reason_id_seq'::regclass);


--
-- Name: oms_order_setting id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.oms_order_setting ALTER COLUMN id SET DEFAULT nextval('public.oms_order_setting_id_seq'::regclass);


--
-- Name: oms_payment_info id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.oms_payment_info ALTER COLUMN id SET DEFAULT nextval('public.oms_payment_info_id_seq'::regclass);


--
-- Name: oms_refund_info id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.oms_refund_info ALTER COLUMN id SET DEFAULT nextval('public.oms_refund_info_id_seq'::regclass);


--
-- Name: undo_log id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.undo_log ALTER COLUMN id SET DEFAULT nextval('public.undo_log_id_seq'::regclass);


--
-- Data for Name: mq_message; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.mq_message (message_id, content, to_exchane, routing_key, class_type, message_status, create_time, update_time) FROM stdin;
\.


--
-- Data for Name: oms_order; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.oms_order (id, member_id, order_sn, coupon_id, create_time, member_username, total_amount, pay_amount, freight_amount, promotion_amount, integration_amount, coupon_amount, discount_amount, pay_type, source_type, status, delivery_company, delivery_sn, auto_confirm_day, integration, growth, bill_type, bill_header, bill_content, bill_receiver_phone, bill_receiver_email, receiver_name, receiver_phone, receiver_post_code, receiver_province, receiver_city, receiver_region, receiver_detail_address, note, confirm_status, delete_status, use_integration, payment_time, delivery_time, receive_time, comment_time, modify_time) FROM stdin;
2058183648504147969	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
2058183835456860162	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
2058183866259828737	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
2058183951899127810	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
2058183996006428673	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
\.


--
-- Data for Name: oms_order_item; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.oms_order_item (id, order_id, order_sn, spu_id, spu_name, spu_pic, spu_brand, category_id, sku_id, sku_name, sku_pic, sku_price, sku_quantity, sku_attrs_vals, promotion_amount, coupon_amount, integration_amount, real_amount, gift_integration, gift_growth) FROM stdin;
\.


--
-- Data for Name: oms_order_operate_history; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.oms_order_operate_history (id, order_id, operate_man, create_time, order_status, note) FROM stdin;
\.


--
-- Data for Name: oms_order_return_apply; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.oms_order_return_apply (id, order_id, sku_id, order_sn, create_time, member_username, return_amount, return_name, return_phone, status, handle_time, sku_img, sku_name, sku_brand, sku_attrs_vals, sku_count, sku_price, sku_real_price, reason, "description述", desc_pics, handle_note, handle_man, receive_man, receive_time, receive_note, receive_phone, company_address) FROM stdin;
\.


--
-- Data for Name: oms_order_return_reason; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.oms_order_return_reason (id, name, sort, status, create_time) FROM stdin;
\.


--
-- Data for Name: oms_order_setting; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.oms_order_setting (id, flash_order_overtime, normal_order_overtime, confirm_overtime, finish_overtime, comment_overtime, member_level) FROM stdin;
\.


--
-- Data for Name: oms_payment_info; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.oms_payment_info (id, order_sn, order_id, alipay_trade_no, total_amount, subject, payment_status, create_time, confirm_time, callback_content, callback_time) FROM stdin;
\.


--
-- Data for Name: oms_refund_info; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.oms_refund_info (id, order_return_id, refund, refund_sn, refund_status, refund_channel, refund_content) FROM stdin;
\.


--
-- Data for Name: undo_log; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.undo_log (id, branch_id, xid, context, rollback_info, log_status, log_created, log_modified, ext) FROM stdin;
\.


--
-- Name: oms_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.oms_order_id_seq', 1, true);


--
-- Name: oms_order_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.oms_order_item_id_seq', 1, true);


--
-- Name: oms_order_operate_history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.oms_order_operate_history_id_seq', 1, true);


--
-- Name: oms_order_return_apply_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.oms_order_return_apply_id_seq', 1, true);


--
-- Name: oms_order_return_reason_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.oms_order_return_reason_id_seq', 1, true);


--
-- Name: oms_order_setting_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.oms_order_setting_id_seq', 1, true);


--
-- Name: oms_payment_info_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.oms_payment_info_id_seq', 1, true);


--
-- Name: oms_refund_info_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.oms_refund_info_id_seq', 1, true);


--
-- Name: undo_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.undo_log_id_seq', 1, true);


--
-- Name: mq_message idx_25047_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.mq_message
    ADD CONSTRAINT idx_25047_primary PRIMARY KEY (message_id);


--
-- Name: oms_order idx_25054_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.oms_order
    ADD CONSTRAINT idx_25054_primary PRIMARY KEY (id);


--
-- Name: oms_order_item idx_25061_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.oms_order_item
    ADD CONSTRAINT idx_25061_primary PRIMARY KEY (id);


--
-- Name: oms_order_operate_history idx_25068_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.oms_order_operate_history
    ADD CONSTRAINT idx_25068_primary PRIMARY KEY (id);


--
-- Name: oms_order_return_apply idx_25075_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.oms_order_return_apply
    ADD CONSTRAINT idx_25075_primary PRIMARY KEY (id);


--
-- Name: oms_order_return_reason idx_25082_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.oms_order_return_reason
    ADD CONSTRAINT idx_25082_primary PRIMARY KEY (id);


--
-- Name: oms_order_setting idx_25087_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.oms_order_setting
    ADD CONSTRAINT idx_25087_primary PRIMARY KEY (id);


--
-- Name: oms_payment_info idx_25092_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.oms_payment_info
    ADD CONSTRAINT idx_25092_primary PRIMARY KEY (id);


--
-- Name: oms_refund_info idx_25099_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.oms_refund_info
    ADD CONSTRAINT idx_25099_primary PRIMARY KEY (id);


--
-- Name: undo_log idx_25106_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.undo_log
    ADD CONSTRAINT idx_25106_primary PRIMARY KEY (id);


--
-- Name: idx_25054_order_sn; Type: INDEX; Schema: public; Owner: faris
--

CREATE UNIQUE INDEX idx_25054_order_sn ON public.oms_order USING btree (order_sn);


--
-- Name: idx_25092_alipay_trade_no; Type: INDEX; Schema: public; Owner: faris
--

CREATE UNIQUE INDEX idx_25092_alipay_trade_no ON public.oms_payment_info USING btree (alipay_trade_no);


--
-- Name: idx_25092_order_sn; Type: INDEX; Schema: public; Owner: faris
--

CREATE UNIQUE INDEX idx_25092_order_sn ON public.oms_payment_info USING btree (order_sn);


--
-- Name: idx_25106_ux_undo_log; Type: INDEX; Schema: public; Owner: faris
--

CREATE UNIQUE INDEX idx_25106_ux_undo_log ON public.undo_log USING btree (xid, branch_id);


--
-- PostgreSQL database dump complete
--

\unrestrict lBc5fimSIRL2LBoKOLIPffUJdCjIBLhR4rdj9pe80soJQCMogrkze5ARGbY2FF1

