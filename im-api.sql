
CREATE TABLE edi_order_first_message (
    fm_id serial,
    im_to varchar(100),
    im_from_account varchar(100),
    from_openid varchar(100),
    to_openid varchar(100),
    clientId varchar(100),
    source_order_id int8,
    target_order_id int8,
    buyer_expected_received_datetime int8,
    order_type varchar(10),
    source_card_name varchar(200),
    target_card_name varchar(200),
    source_address varchar(500),
    target_address varchar(500),
    materiels varchar(200),
    total_materiel_amount decimal(64,19),
    total_price decimal(64,19),
    source_order_no varchar (100),
    target_order_no varchar (100),
    source_order_url text,
    target_order_url text,
    materiel_img text,
    CONSTRAINT edi_order_first_message_fm_id PRIMARY KEY(fm_id)
);

