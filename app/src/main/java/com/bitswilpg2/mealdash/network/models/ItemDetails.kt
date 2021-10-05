package com.bitswilpg2.mealdash.network.models


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ItemDetails(
    val description: String,
    @SerializedName("end_time")
    val endTime: String,
    val id: Int,
    val price: Double,
    @SerializedName("start_time")
    val startTime: String,
    val status: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(description)
        parcel.writeString(endTime)
        parcel.writeInt(id)
        parcel.writeDouble(price)
        parcel.writeString(startTime)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemDetails> {
        override fun createFromParcel(parcel: Parcel): ItemDetails {
            return ItemDetails(parcel)
        }

        override fun newArray(size: Int): Array<ItemDetails?> {
            return arrayOfNulls(size)
        }
    }
}