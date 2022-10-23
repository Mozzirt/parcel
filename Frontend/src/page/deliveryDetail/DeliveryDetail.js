import './DeliveryDetail.scss'

function DeliveryDetail(props) {
    return (
        <div className="detail-main-container">
            <div className="top-area">
                <div className="delivery-info-area">
                    <div className="logo">
                        <img src='/asset/ci/circle-large/lotte.svg' alt='logo'></img>
                    </div>
                    <div className="delivery-info">
                        <div className="delivery-name">
                            롯데택배 (현대택배)
                        </div>
                        <div className="detail-info">
                            <div>
                                접수일자 : <span>2022-09-13 21:00</span>
                            </div>
                            <div className="tracking-number-area">
                                운송장번호 : <span className='tracking-number'> 220-S0-227932323..</span>
                                <img src='/asset/etc/copy.svg' alt='copy'></img>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="delivery-status">
                    <div className='icon'>
                        <img src='/asset/deliveryStatus/인수.svg' alt='인수'></img>
                        <img src='/asset/deliveryStatus/이동중.svg' alt='인수'></img>
                        <img src='/asset/deliveryStatus/배달지.svg' alt='인수'></img>
                        <img src='/asset/deliveryStatus/배송중.svg' alt='인수'></img>
                        <img src='/asset/deliveryStatus/완료.svg' alt='인수'></img>
                        <div className='division'></div>

                    </div>
                    <div className='text'>
                        <div className='status'>인수</div>
                        <div className='status'>이동중</div>
                        <div className='status'>배달중</div>
                        <div className='status'>배송중</div>
                        <div className='status'>완료</div>
                    </div>
                </div>
            </div>
            <div className="bottom-area">
                <div className="page-name">

                </div>
                <div className="delivery-step"></div>
                <div className="delivery-history-card">
                    <div className="history-date"></div>
                    <div className="history-location">
                        <div className="status">

                        </div>
                        <div className="status-value">
                            
                        </div>
                    </div>
                    <div className="history-status-info"></div>
                        <div className="status">

                        </div>
                        <div className="status-value">

                        </div>
                </div>
            </div>
        </div>
    )
}

export default DeliveryDetail