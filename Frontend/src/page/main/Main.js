import './Main.scss'
import Footer from '../../component/nav/footer/Footer'
import SelectDeliveryCompany from '../../component/drawer/SelectDeliveryCompany'
import React, { useState } from 'react'
import Loading from '../../component/loading/xm/Loading'

const Cards = () => {
    const data = [
        {
            name: '00',
            date: "2022-09-13 21:00",
            trackingNum: "43760201478243",
            sender: '무찌',
            deliveryImg: '/asset/ci/lotte_circle.svg',
            deliveryName: '롯데택배',
            status: "이동중"
        },
        {
            name: '초코 모찌',
            date: "2022-09-15 21:00",
            trackingNum: "43760201478242",
            sender: '무찌',
            deliveryImg: '/asset/ci/lotte_circle.svg',
            deliveryName: '롯데택배',
            status: "상품 준비중"
        },
    ]

    return (
        <div className="card-list-area">
            {
                data.map((item) => (
                    <div className="card" key={item.trackingNum}>
                        <div className='card-img-area'>
                            <img src={item.deliveryImg} alt='' />
                            <div className='delivery-name'>{item.deliveryName}</div>
                        </div>
                        <div className='card-info-area'>
                            <div className='item-name'>상품명 : {item.name}</div>
                            <div className='item-detail-area'>
                                <div className='item-detail date'>접수일자 : <span>{item.date}</span></div>
                                <div className='item-detail tracking-num'>운송장 번호 : <span>{item.trackingNum}</span></div>
                                <div className='item-detail sender'>구매처 : <span>{item.sender}</span></div>
                            </div>
                        </div>
                        <div className='card-delivery-status button'>{item.status}<div className='arrow'>&gt;</div></div>
                    </div>
                ))
            }
        </div>
    )
}

function Main() {
    const [buttonActiveIndex, setButtonActiveIndex] = useState(0)
    const [modalOpen, setModalOpen] = useState(false)
    
    const toggleActive = index => {
        setButtonActiveIndex(index)
    }

    const search = () => {
        setModalOpen(!modalOpen)
    }

    return (
        <div className='main-root-container'>
            <div className='main-top-area'>
                <div className='button notification'>
                    <img src='/asset/notification.svg' alt='알림'></img>
                </div>
                <div className='top-text-box'>
                    운송장번호로<br />
                    택배를 조회해보세요
                </div>
                <div className='search-area'>
                    <div className='parcel-box'>
                        <img src='/asset/parcel-box.svg' alt='박스'></img>
                    </div>
                    <input className='search-input' type="text" />
                    <div className='button search'><img src='/asset/search.svg' alt='검색' onClick={search} /></div>
                </div>
                <div className='recommend-area'>
                    <div className='company-icon button'>
                        <img src='/asset/ci/circle-small/lotte.svg' alt='롯데'></img>
                        롯데택배
                    </div>
                    <div className='company-icon button'>
                        <img src='/asset/ci/circle-small/lotte.svg' alt='롯데'></img>
                        롯데택배
                    </div>
                    <div className='company-icon button'>
                        <img src='/asset/ci/circle-small/lotte.svg' alt='롯데'></img>
                        롯데택배
                    </div>
                </div>
            </div>
            <div className='main-button-area'>
                <div className='button delivery-status'>
                    <div className={'left' + (buttonActiveIndex == 0 ? ' selected' : '')} onClick={() => toggleActive(0)}>
                        배송현황
                    </div>
                    <div className='separator'></div>
                    <div className={'right' + (buttonActiveIndex == 1 ? ' selected' : '')} onClick={() => toggleActive(1)}>
                        배송완료
                    </div>
                </div>
            </div>
            <div className='main-content'>
                <div className='sort-area'>
                    <div className='left'>
                        최신순
                    </div>
                    <div className='button right'>
                        검색조건&#11139;
                    </div>
                </div>
                <Cards />
            </div>
            <Footer />
            <SelectDeliveryCompany display={modalOpen} closeModal={() => setModalOpen(false)}></SelectDeliveryCompany>
        </div>
    );
}

export default Main