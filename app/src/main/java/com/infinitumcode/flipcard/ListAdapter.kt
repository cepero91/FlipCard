package com.infinitumcode.flipcard

import android.animation.AnimatorInflater
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(private val listener: OnClickListener) :
    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private val listData: List<String> = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return MyViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.restoreInitialState()
    }

    override fun getItemCount(): Int = listData.size

    class MyViewHolder(view: View, val listener: OnClickListener) : RecyclerView.ViewHolder(view) {

        // internal logic
        var backVisible: Boolean = false
        val handler = Handler(Looper.myLooper()!!)

        // views
        var tvFront: TextView? = null
        var tvBack: TextView? = null
        var cardFront: FrameLayout? = null
        var cardBack: FrameLayout? = null

        //animator set
        val animatorLeftIn = AnimatorInflater.loadAnimator(itemView.context, R.animator.in_animator)
        val animatorRightOut =
            AnimatorInflater.loadAnimator(itemView.context, R.animator.out_animator)

        fun bind(item: String) {
            tvFront = itemView.findViewById(R.id.tv_front)
            tvBack = itemView.findViewById(R.id.tv_back)
            cardFront = itemView.findViewById(R.id.front)
            cardBack = itemView.findViewById(R.id.back)
            tvFront?.text = "Front $item"
            tvBack?.text = "Back $item"

            //distance
            val distance = 8000
            val scale: Float = itemView.resources.displayMetrics.density * distance
            cardFront?.cameraDistance = scale
            cardBack?.cameraDistance = scale

            itemView.setOnClickListener {
                listener.onCardClick(adapterPosition, item)
                if (backVisible.not()) {
                    showBack()
                    handler.postDelayed({
                        showFront()
                    }, 3000)
                } else {
                    showFront()
                }
            }

        }

        internal fun restoreInitialState() {
            handler.removeCallbacksAndMessages(null)
            backVisible = false
            animatorLeftIn.cancel()
            animatorRightOut.cancel()
            cardFront?.rotationY = 0f
            cardFront?.alpha = 1f
        }

        private fun showFront() {
            backVisible = false
            animatorRightOut.setTarget(cardBack)
            animatorLeftIn.setTarget(cardFront)
            animatorRightOut.start()
            animatorLeftIn.start()
        }

        private fun showBack() {
            backVisible = true
            animatorRightOut.setTarget(cardFront)
            animatorLeftIn.setTarget(cardBack)
            animatorRightOut.start()
            animatorLeftIn.start()
        }
    }
}