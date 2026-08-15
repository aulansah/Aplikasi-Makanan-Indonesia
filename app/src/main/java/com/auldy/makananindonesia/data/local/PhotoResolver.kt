package com.auldy.makananindonesia.data.local

import com.auldy.makananindonesia.R

/**
 * Resolves a stable, human-readable photo key (e.g. "bakso") into the actual
 * compiled drawable resource ID (R.drawable.bakso), and vice versa.
 *
 * WHY THIS EXISTS — bug fix note:
 * The previous version stored the raw Android resource Int (R.drawable.bakso)
 * directly inside the Room database. Resource IDs are only guaranteed stable
 * within a single compiled build — they can shift between builds (different
 * AGP/AAPT2 runs, resource additions/removals, non-transitive R class, etc).
 * Because Room only seeds the database once (on first app creation) and then
 * keeps reusing the same row values, a rebuilt/updated APK could end up with
 * stale integers that no longer point to any real drawable — which is exactly
 * why some food photos silently failed to appear.
 *
 * The fix: persist a STABLE STRING KEY in the database instead, and resolve it
 * to the current build's real resource ID here, at read time, via a plain
 * `when` (fully compile-time safe — no reflection, no getIdentifier()).
 */
object PhotoResolver {

    private val keyToRes: Map<String, Int> = mapOf(
        "bakso" to R.drawable.bakso,
        "gudeg" to R.drawable.gudeg,
        "mieayam" to R.drawable.mieayam,
        "nasigoreng" to R.drawable.nasigoreng,
        "pecel" to R.drawable.pecel,
        "pempek" to R.drawable.pempek,
        "rawon" to R.drawable.rawon,
        "rendang" to R.drawable.rendang,
        "sate" to R.drawable.sate,
        "soto" to R.drawable.soto
    )

    private val resToKey: Map<Int, String> = keyToRes.entries.associate { (k, v) -> v to k }

    /** Fallback shown if a key is ever unrecognized (e.g. corrupted/old data). */
    private val fallbackRes: Int = R.drawable.bakso

    fun resolve(key: String): Int = keyToRes[key] ?: fallbackRes

    fun keyFor(resId: Int): String = resToKey[resId] ?: "bakso"
}
