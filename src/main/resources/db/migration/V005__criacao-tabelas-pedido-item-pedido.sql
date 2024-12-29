create table pedido (
    id bigint not null auto_increment,
    subtotal decimal(10,2) not null,
    taxa_frete decimal(10,2) not null,
    valor_total decimal(10,2) not null,

    forma_pagamento_id bigint not null,
    restaurante_id bigint not null,
    usuario_cliente_id bigint not null,

    status varchar(10) not null ,
    data_criacao datetime not null,
    data_confirmacao datetime null,
    data_cancelamento datetime null,
    data_entrega datetime null,

    primary key (id),

    constraint fk_pedido_restaurante foreign key  (restaurante_id) references restaurante (id),
    constraint fk_pedido_usuario_cliente foreign key (usuario_cliente_id) references usuario(id),
    constraint fk_pedido_forma_pagamento foreign key (forma_pagamento_id) references forma_pagamento(id)
) engine=InnoDB;

create table item_pedido(
    id bigint not null auto_increment,
    quantidade smallint(6) not null ,
    preco_unitario decimal(10,2) not null,
    preco_total decimal(10,2) not null,
    observacao text,

    pedido_id bigint not null,
    produto_id bigint not null,

    primary key (id),
    unique key uk_item_pedido_produto (pedido_id, produto_id),

    constraint fk_pedido_pedido foreign key (pedido_id) references pedido (id),
    constraint fk_pedido_produto foreign key (produto_id) references produto(id)
)engine=InnoDB;