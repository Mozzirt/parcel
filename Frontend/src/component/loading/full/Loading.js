import React from "react";
import Lottie from "lottie-react";
import loading from "./loading.json";

import './Loading.css'

const style = {
    height: '200px',
    width: '200px',
};
const Loading = () => {
    return (
        <div className="wrapper">
            <div className="circle"></div>
            <Lottie animationData={loading} loop={true} style={style} />
        </div>
    )
} 

export default Loading;