package com.nammakathey.app.ui.adapters

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nammakathey.app.data.model.*
import com.nammakathey.app.databinding.*
import com.nammakathey.app.util.LanguageManager
import com.nammakathey.app.util.toColorInt
import com.nammakathey.app.util.toFormattedDate

// ─── Onboarding ───────────────────────────────────────────────────────────────
class OnboardingAdapter(private val pages: List<com.nammakathey.app.ui.OnboardingPage>) :
    RecyclerView.Adapter<OnboardingAdapter.VH>() {
    inner class VH(val b: ItemOnboardingPageBinding) : RecyclerView.ViewHolder(b.root)
    override fun onCreateViewHolder(p: ViewGroup, t: Int) =
        VH(ItemOnboardingPageBinding.inflate(LayoutInflater.from(p.context), p, false))
    override fun getItemCount() = pages.size
    override fun onBindViewHolder(h: VH, pos: Int) {
        val page = pages[pos]
        h.b.pageEmoji.text = page.emoji
        h.b.pageTitleEn.text = page.titleEn
        h.b.pageDescEn.text = page.descEn
    }
}

// ─── District Chip ────────────────────────────────────────────────────────────
class DistrictAdapter(private val onClick: (District) -> Unit) :
    ListAdapter<District, DistrictAdapter.VH>(object : DiffUtil.ItemCallback<District>() {
        override fun areItemsTheSame(a: District, b: District) = a.districtId == b.districtId
        override fun areContentsTheSame(a: District, b: District) = a == b
    }) {
    private var selectedPos = 0
    fun setSelected(pos: Int) { selectedPos = pos; notifyDataSetChanged() }
    fun setLanguage(kn: Boolean) { notifyDataSetChanged() }
    inner class VH(val b: ItemDistrictChipBinding) : RecyclerView.ViewHolder(b.root)
    override fun onCreateViewHolder(p: ViewGroup, t: Int) =
        VH(ItemDistrictChipBinding.inflate(LayoutInflater.from(p.context), p, false))
    override fun onBindViewHolder(h: VH, pos: Int) {
        val district = getItem(pos)
        val isKn = LanguageManager.isKannada
        val color = district.colorHex.toColorInt()
        h.b.districtName.text = if (isKn) district.districtNameKn else district.districtName
        if (pos == selectedPos) {
            h.b.chipBg.setCardBackgroundColor(color); h.b.districtName.setTextColor(Color.WHITE)
        } else {
            h.b.chipBg.setCardBackgroundColor(Color.WHITE); h.b.districtName.setTextColor(color)
        }
        h.b.root.setOnClickListener {
            val old = selectedPos; selectedPos = pos
            notifyItemChanged(old); notifyItemChanged(pos); onClick(district)
        }
    }
}

// ─── Hero Card ────────────────────────────────────────────────────────────────
class HeroCardAdapter(
    private var isKannada: Boolean,
    private val onHeroClick: (Hero, District) -> Unit
) : RecyclerView.Adapter<HeroCardAdapter.VH>() {
    private var heroes: List<Hero> = emptyList()
    private var district: District? = null
    fun submitHeroes(h: List<Hero>, d: District) { heroes = h; district = d; notifyDataSetChanged() }
    fun setLanguage(kn: Boolean) { isKannada = kn; notifyDataSetChanged() }
    inner class VH(val b: ItemHeroCardBinding) : RecyclerView.ViewHolder(b.root)
    override fun getItemCount() = heroes.size
    override fun onCreateViewHolder(p: ViewGroup, t: Int) =
        VH(ItemHeroCardBinding.inflate(LayoutInflater.from(p.context), p, false))
    override fun onBindViewHolder(h: VH, pos: Int) {
        val hero = heroes[pos]; val dist = district ?: return
        val isKn = LanguageManager.isKannada
        val color = hero.colorHex.toColorInt()
        h.b.heroEmoji.text = hero.imageEmoji
        h.b.heroName.text = hero.displayName(isKn)
        h.b.heroCategory.text = hero.displayCategory(isKn)
        h.b.heroEra.text = hero.era
        h.b.cardBg.setCardBackgroundColor(color)
        h.b.heroName.setTextColor(Color.WHITE)
        h.b.heroCategory.setTextColor(Color.argb(200, 255, 255, 255))
        h.b.heroEra.setTextColor(Color.argb(180, 255, 255, 255))
        h.b.root.setOnClickListener {
            h.b.root.animate().scaleX(0.96f).scaleY(0.96f).setDuration(70).withEndAction {
                h.b.root.animate().scaleX(1f).scaleY(1f).setDuration(70).start()
                onHeroClick(hero, dist)
            }.start()
        }
    }
}

// ─── Story Page ───────────────────────────────────────────────────────────────
class StoryPagerAdapter(
    private val pages: List<StoryPage>,
    private var isKannada: Boolean,
    private val colorHex: String
) : RecyclerView.Adapter<StoryPagerAdapter.VH>() {
    fun setLanguage(kn: Boolean) { isKannada = kn; notifyDataSetChanged() }
    inner class VH(val b: ItemStoryPageBinding) : RecyclerView.ViewHolder(b.root)
    override fun getItemCount() = pages.size
    override fun onCreateViewHolder(p: ViewGroup, t: Int) =
        VH(ItemStoryPageBinding.inflate(LayoutInflater.from(p.context), p, false))
    override fun onBindViewHolder(h: VH, pos: Int) {
        val page = pages[pos]; val isKn = LanguageManager.isKannada
        h.b.pageTitle.text = page.displayTitle(isKn)
        h.b.pageContent.text = page.displayContent(isKn)
        h.b.pageNumber.text = "Page ${page.pageNumber}"
        val color = colorHex.toColorInt()
        h.b.pageTitle.setTextColor(color)
        h.b.decorBar.setBackgroundColor(color)
    }
}

// ─── Badge ────────────────────────────────────────────────────────────────────
class BadgeAdapter(private var isKannada: Boolean) :
    ListAdapter<BadgeEntity, BadgeAdapter.VH>(object : DiffUtil.ItemCallback<BadgeEntity>() {
        override fun areItemsTheSame(a: BadgeEntity, b: BadgeEntity) = a.badgeId == b.badgeId
        override fun areContentsTheSame(a: BadgeEntity, b: BadgeEntity) = a == b
    }) {
    fun setLanguage(kn: Boolean) { isKannada = kn; notifyDataSetChanged() }
    inner class VH(val b: ItemBadgeBinding) : RecyclerView.ViewHolder(b.root)
    override fun onCreateViewHolder(p: ViewGroup, t: Int) =
        VH(ItemBadgeBinding.inflate(LayoutInflater.from(p.context), p, false))
    override fun onBindViewHolder(h: VH, pos: Int) {
        val badge = getItem(pos); val isKn = LanguageManager.isKannada
        val color = badge.districtColorHex.toColorInt()
        h.b.badgeEmoji.text = badge.heroEmoji
        h.b.badgeName.text = if (isKn) badge.badgeNameKn else badge.badgeNameEn
        h.b.heroName.text = if (isKn) badge.heroNameKn else badge.heroNameEn
        h.b.districtName.text = if (isKn) badge.districtNameKn else badge.districtName
        h.b.earnedDate.text = badge.earnedDate.toFormattedDate()
        h.b.badgeCard.setCardBackgroundColor(color)
        h.b.badgeName.setTextColor(Color.WHITE)
        h.b.heroName.setTextColor(Color.argb(220, 255, 255, 255))
        h.b.districtName.setTextColor(Color.argb(180, 255, 255, 255))
        h.b.earnedDate.setTextColor(Color.argb(160, 255, 255, 255))
    }
}

// ─── Search Result ────────────────────────────────────────────────────────────
class SearchResultAdapter(
    private var isKannada: Boolean,
    private val onClick: (Hero, District) -> Unit
) : ListAdapter<Pair<Hero, District>, SearchResultAdapter.VH>(object : DiffUtil.ItemCallback<Pair<Hero, District>>() {
    override fun areItemsTheSame(a: Pair<Hero, District>, b: Pair<Hero, District>) = a.first.heroId == b.first.heroId
    override fun areContentsTheSame(a: Pair<Hero, District>, b: Pair<Hero, District>) = a == b
}) {
    fun setLanguage(kn: Boolean) { isKannada = kn; notifyDataSetChanged() }
    inner class VH(val b: ItemSearchResultBinding) : RecyclerView.ViewHolder(b.root)
    override fun onCreateViewHolder(p: ViewGroup, t: Int) =
        VH(ItemSearchResultBinding.inflate(LayoutInflater.from(p.context), p, false))
    override fun onBindViewHolder(h: VH, pos: Int) {
        val (hero, district) = getItem(pos); val isKn = LanguageManager.isKannada
        val color = hero.colorHex.toColorInt()
        h.b.heroEmoji.text = hero.imageEmoji
        h.b.heroName.text = hero.displayName(isKn)
        h.b.heroCategory.text = hero.displayCategory(isKn)
        h.b.districtName.text = if (isKn) district.districtNameKn else district.districtName
        h.b.heroEra.text = hero.era
        h.b.emojiContainer.setCardBackgroundColor(color)
        h.b.root.setOnClickListener { onClick(hero, district) }
    }
}

// ─── Bookmark ─────────────────────────────────────────────────────────────────
class BookmarkAdapter(
    private var isKannada: Boolean,
    private val onClick: (BookmarkEntity) -> Unit
) : ListAdapter<BookmarkEntity, BookmarkAdapter.VH>(object : DiffUtil.ItemCallback<BookmarkEntity>() {
    override fun areItemsTheSame(a: BookmarkEntity, b: BookmarkEntity) = a.heroId == b.heroId
    override fun areContentsTheSame(a: BookmarkEntity, b: BookmarkEntity) = a == b
}) {
    fun setLanguage(kn: Boolean) { isKannada = kn; notifyDataSetChanged() }
    inner class VH(val b: ItemBookmarkBinding) : RecyclerView.ViewHolder(b.root)
    override fun onCreateViewHolder(p: ViewGroup, t: Int) =
        VH(ItemBookmarkBinding.inflate(LayoutInflater.from(p.context), p, false))
    override fun onBindViewHolder(h: VH, pos: Int) {
        val bookmark = getItem(pos); val isKn = LanguageManager.isKannada
        val color = bookmark.colorHex.toColorInt()
        h.b.heroEmoji.text = bookmark.emoji
        h.b.heroName.text = if (isKn) bookmark.heroNameKn else bookmark.heroNameEn
        h.b.districtName.text = bookmark.districtName
        h.b.savedDate.text = bookmark.savedAt.toFormattedDate()
        h.b.emojiBg.setCardBackgroundColor(color)
        h.b.root.setOnClickListener { onClick(bookmark) }
    }
}

// ─── Statue ───────────────────────────────────────────────────────────────────
class StatueAdapter(
    private var isKannada: Boolean,
    private val items: List<Triple<StatueLocation, Hero, Double?>>
) : RecyclerView.Adapter<StatueAdapter.VH>() {
    fun setLanguage(kn: Boolean) { isKannada = kn; notifyDataSetChanged() }
    inner class VH(val b: ItemStatueBinding) : RecyclerView.ViewHolder(b.root)
    override fun getItemCount() = items.size
    override fun onCreateViewHolder(p: ViewGroup, t: Int) =
        VH(ItemStatueBinding.inflate(LayoutInflater.from(p.context), p, false))
    override fun onBindViewHolder(h: VH, pos: Int) {
        val (statue, hero, distKm) = items[pos]
        val isKn = LanguageManager.isKannada
        val color = hero.colorHex.toColorInt()
        val ctx   = h.b.root.context

        h.b.heroEmoji.text    = hero.imageEmoji
        h.b.statueName.text   = statue.displayName(isKn)
        h.b.heroName.text     = hero.displayName(isKn)
        h.b.statueAddress.text = statue.address
        h.b.distanceText.text = if (distKm != null)
            "${String.format("%.1f", distKm)} km " + if (isKn) "ದೂರ" else "away"
        else if (isKn) "ದೂರ ತಿಳಿಯಿಲ್ಲ" else "Distance unknown"
        h.b.emojiBg.setCardBackgroundColor(color)

        // ── Google Maps button ───────────────────────────────────────────────
        h.b.btnMaps.setOnClickListener {
            val uri = Uri.parse(statue.mapsUrl())
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                setPackage("com.google.android.apps.maps") // open in Maps app if installed
            }
            // Fall back to browser if Maps app not installed
            if (intent.resolveActivity(ctx.packageManager) != null) {
                ctx.startActivity(intent)
            } else {
                ctx.startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }

        // ── Wikipedia button ─────────────────────────────────────────────────
        val wikiUrl = statue.wikiUrl()
        if (wikiUrl != null) {
            h.b.btnWiki.visibility = View.VISIBLE
            h.b.btnWiki.setOnClickListener {
                ctx.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl)))
            }
        } else {
            // No Wikipedia URL — show button but greyed out / disabled
            h.b.btnWiki.visibility = View.VISIBLE
            h.b.btnWiki.alpha = 0.35f
            h.b.btnWiki.isClickable = false
        }
    }
}
